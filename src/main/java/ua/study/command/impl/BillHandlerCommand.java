package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class BillHandlerCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        User client = (User) session.getAttribute("client");
        Dates dates = (Dates) session.getAttribute("dates");
        session.removeAttribute("dates");
        Map<RoomType, Integer> reservedRoomTypes = (Map<RoomType, Integer>) session.getAttribute("reservedRoomTypes");
        session.removeAttribute("reservedRoomTypes");

        ReservationService reservationService = ServiceFactory.getInstance().getService(ReservationService.class);
        Reservation reservation = reservationService.reservation(dates, client, reservedRoomTypes);
        if(reservation == null) {
            session.setAttribute("error", "error.no_rooms");
            response.sendRedirect("/reservation");
            return;
        }

        session.setAttribute("reservation", reservation);
        response.sendRedirect("/confirmation");
    }
}
