package ua.study.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomService;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 03.04.17.
 */
public class DatesHandlerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DatesHandlerCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate arrive;
        LocalDate departure;
        try {
            arrive = LocalDate.parse(request.getParameter("arriveDatepicker"), formatter);
            departure = LocalDate.parse(request.getParameter("departureDatepicker"), formatter);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            request.setAttribute("error", "all fields should be filled correct");
            request.getRequestDispatcher("/WEB-INF/jsp/check_dates.jsp").include(request, response);
            return;
        }
        if (!Validator.getInstance().isReservationDatesValid(arrive, departure)) {
            request.setAttribute("error", "all fields should be filled correct");
            request.getRequestDispatcher("/WEB-INF/jsp/check_dates.jsp").include(request, response);
            return;
        }
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);
        //List<Room> freeRooms = roomService.getFreeRooms(arrive, departure);
        Map<RoomType, Integer> freeRooms = roomTypeService.getFreeRoomTypes(arrive, departure);
        List<RoomType> roomTypes = roomTypeService.getRoomTypes();
        request.setAttribute("freeRooms", freeRooms);
        request.setAttribute("roomTypes", roomTypes);
        request.getSession().setAttribute("arrive", arrive);
        request.getSession().setAttribute("departure", departure);
        request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
        //response.sendRedirect("./reservation");
    }
}
