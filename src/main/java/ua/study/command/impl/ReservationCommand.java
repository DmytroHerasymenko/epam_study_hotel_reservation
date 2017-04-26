package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.RoomType;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("arrive") == null) {
            request.setAttribute("error", "all dates fields should be filled correct");
            request.getRequestDispatcher("/WEB-INF/jsp/check_dates.jsp").include(request, response);
            return;
        }
        LocalDate arrive = (LocalDate) session.getAttribute("arrive");
        LocalDate departure = (LocalDate) session.getAttribute("departure");
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);
        Map<RoomType, Integer> freeRoomTypes = roomTypeService.getFreeRoomTypes(arrive, departure);
        request.setAttribute("freeRooms", freeRoomTypes);
        request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
    }
}
