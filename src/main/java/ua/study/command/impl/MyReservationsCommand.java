package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.dao.impl.connection.TransactionHelper;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.User;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;
import ua.study.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dima on 12.04.17.
 */
public class MyReservationsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ReservationService reservationService = ServiceFactory.getInstance().getService(ReservationService.class);
        ReservedRoomService reservedRoomService = ServiceFactory.getInstance().getService(ReservedRoomService.class);
        User client = (User) session.getAttribute("client");

        List<Reservation> reservations = reservationService.getReservations(client);
        List<ReservedRoom> reservedRooms = reservedRoomService.getUserReservedRooms(reservations);

        setReservedRooms(reservations, reservedRooms);

        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/jsp/my_reservations.jsp").include(request, response);
    }

    private void setReservedRooms(List<Reservation> reservations, List<ReservedRoom> reservedRooms){
        reservations.forEach(reservation ->
                reservation.setReservedRooms(getReservedRoomsById(reservation, reservedRooms)));
    }

    private List<ReservedRoom> getReservedRoomsById(Reservation reservation, List<ReservedRoom> reservedRooms){
        List<ReservedRoom> localReservedRooms = reservedRooms.stream()
                .filter(reservedRoom -> reservation.getReservationId() == reservedRoom.getReservationId())
                .collect(Collectors.toList());
        reservedRooms.removeAll(localReservedRooms);
        return localReservedRooms;
    }
}