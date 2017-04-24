package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationHandlerCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object reservedRoomTypesMap = request.getAttribute("reservedRoomTypes");
        if(isReservedRoomsValid(reservedRoomTypesMap, request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        String login = String.valueOf(((User) session.getAttribute("client")).getLogin());
        LocalDate arrive = (LocalDate) session.getAttribute("arrive");
        LocalDate departure = (LocalDate) session.getAttribute("departure");
        Map<RoomType, Integer> reservedRoomTypes = (Map<RoomType, Integer>) request.getAttribute("reservedRoomTypes");

        ReservationService reservationService =
                ServiceFactory.getInstance().getService("ReservationService", ReservationService.class);
        Reservation reservation = reservationService.reservation(login, arrive, departure, reservedRoomTypes);
        if(reservation == null) {
            request.setAttribute("error", "sorry, we do not have enough rooms on the selected dates.");
            response.sendRedirect("./reservation");
            return;
        }
        setReservation(reservation, arrive, departure);

        session.removeAttribute("arrive");
        session.removeAttribute("departure");
        request.setAttribute("reservedRoomTypes", reservedRoomTypes);
        session.setAttribute("reservation", reservation);
        session.setAttribute("totalPrice", totalPrice(reservedRoomTypes));
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
    }

    private void setReservation(Reservation reservation, LocalDate arrive, LocalDate departure){
        ReservedRoomService reservedRoomService =
                ServiceFactory.getInstance().getService("ReservedRoomService", ReservedRoomService.class);
        List<ReservedRoom> reservedRooms = reservedRoomService.getReservedRooms(reservation.getReservationId());
        reservation.setReservedRooms(reservedRooms);
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);

    }

    private boolean isReservedRoomsValid(Object reservedRoomTypesMap, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        if(!Validator.getInstance().isReservedRoomsQuantityValid(reservedRoomTypesMap)) {
            request.setAttribute("error", "all fields should be filled correct");
            response.sendRedirect("./reservation");
            return false;
        }
        return true;
    }

    private double totalPrice(Map<RoomType, Integer> reservedRoomTypes){
        double totalPrice = 0;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            totalPrice += entry.getKey().getPrice() * (double) entry.getValue();
        }
        return totalPrice;
    }
}
