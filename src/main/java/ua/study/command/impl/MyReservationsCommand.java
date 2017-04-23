package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;
import ua.study.service.impl.ServiceFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by dima on 12.04.17.
 */
public class MyReservationsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ReservationService reservationService =
                ServiceFactoryImpl.getInstance().getService("ReservationService", ReservationService.class);
        ReservedRoomService reservedRoomService =
                ServiceFactoryImpl.getInstance().getService("ReservedRoomService", ReservedRoomService.class);
        List<Reservation> reservationList = reservationService.getReservations(String.valueOf(session.getAttribute("login")));

        for(Reservation r : reservationList){
            List<ReservedRoom> reservedRoom = reservedRoomService.getReservedRooms(r.getReservationId());
            r.setReservedRoomList(reservedRoom);
        }
        request.setAttribute("reservs", reservationList);
        request.getRequestDispatcher("/WEB-INF/jsp/my_reservations.jsp").include(request, response);
    }
}
