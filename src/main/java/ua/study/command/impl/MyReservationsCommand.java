package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.dao.impl.executor.TransactionHelper;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;
import ua.study.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 12.04.17.
 */
public class MyReservationsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ReservationService reservationService =
                ServiceFactory.getInstance().getService("ReservationService", ReservationService.class);
        ReservedRoomService reservedRoomService =
                ServiceFactory.getInstance().getService("ReservedRoomService", ReservedRoomService.class);
        String login = String.valueOf(session.getAttribute("login"));
        TransactionHelper.getInstance().beginTransaction();
        List<Reservation> reservations = reservationService.getReservations(login);
        List<ReservedRoom> reservedRooms = reservedRoomService.getUserReservedRooms(login);
        TransactionHelper.getInstance().commitTransaction();

        setReservedRooms(reservations, reservedRooms);
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/jsp/my_reservations.jsp").include(request, response);
    }

    private void setReservedRooms(List<Reservation> reservations, List<ReservedRoom> reservedRooms){
        List<ReservedRoom> localReservedRooms;
        for(Reservation reservation : reservations){
            localReservedRooms = getReservedRooms(reservation, reservedRooms);
            reservation.setReservedRooms(localReservedRooms);
        }
    }

    private List<ReservedRoom> getReservedRooms(Reservation reservation, List<ReservedRoom> reservedRooms){
        List<ReservedRoom> localReservedRooms = new ArrayList<>();
        int size = reservedRooms.size();
        for(int i = 0; i < size; i++){
            if(reservation.getReservationId() == reservedRooms.get(i).getReservationId()){
                localReservedRooms.add(reservedRooms.remove(i));
                i--;
            }
        }
        return localReservedRooms;
    }
}
