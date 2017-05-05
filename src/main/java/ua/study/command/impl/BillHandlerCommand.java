package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class BillHandlerCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Map<RoomType, Integer> reservedRoomTypes = (Map<RoomType, Integer>) session.getAttribute("reservedRoomTypes");
        User client = (User) session.getAttribute("client");
        Reservation reservation = (Reservation) session.getAttribute("reservation");
        reservation.setClientLogin(client.getLogin());

        ReservationService reservationService = ServiceFactory.getInstance().getService(ReservationService.class);
        reservation = reservationService.reservation(reservation, reservedRoomTypes);
        if(reservation == null) {
            session.setAttribute("error", "sorry, we do not have enough rooms on the selected dates.");
            response.sendRedirect("/reservation");
            return;
        }
        setReservedRooms(reservation);

        request.setAttribute("reservation", reservation);
        session.removeAttribute("reservation");
        session.removeAttribute("reservedRoomTypes");
        request.getRequestDispatcher("/WEB-INF/jsp/confirmation.jsp").include(request, response);
    }

    private void setReservedRooms(Reservation reservation){
        ReservedRoomService reservedRoomService = ServiceFactory.getInstance().getService(ReservedRoomService.class);
        List<ReservedRoom> reservedRooms = reservedRoomService.getReservedRooms(reservation.getReservationId());
        reservation.setReservedRooms(reservedRooms);
    }
}
