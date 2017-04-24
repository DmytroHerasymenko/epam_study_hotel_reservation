package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Created by dima on 03.04.17.
 */
public class DatesHandlerCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String arriveDate = request.getParameter("arriveDatepicker");
        String departureDate = request.getParameter("departureDatepicker");
        if (!Validator.getInstance().isReservationDatesValid(arriveDate, departureDate)) {
            request.setAttribute("error", "all dates fields should be filled correct");
            request.getRequestDispatcher("/WEB-INF/jsp/check_dates.jsp").include(request, response);
            return;
        }
        HttpSession session = request.getSession(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate arrive = LocalDate.parse(arriveDate, formatter);
        LocalDate departure = LocalDate.parse(departureDate, formatter);
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);

        Map<RoomType, Integer> freeRoomTypes = roomTypeService.getFreeRoomTypes(arrive, departure);
        request.setAttribute("freeRooms", freeRoomTypes);
        session.setAttribute("arrive", arrive);
        session.setAttribute("departure", departure);
        request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
    }
}
