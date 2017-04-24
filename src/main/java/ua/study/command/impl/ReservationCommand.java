package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.RoomType;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate arrive = LocalDate.parse(request.getParameter("arriveDatepicker"), formatter);
        LocalDate departure = LocalDate.parse(request.getParameter("departureDatepicker"), formatter);
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);
        Map<RoomType, Integer> freeRoomTypes = roomTypeService.getFreeRoomTypes(arrive, departure);
        request.setAttribute("freeRooms", freeRoomTypes);
        request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
    }
}
