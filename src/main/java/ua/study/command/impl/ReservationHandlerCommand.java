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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationHandlerCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String json = getJson(request);

        if(!Validator.getInstance().isReservedRoomsQuantityValid(json)){
            session.setAttribute("error", "all fields should be filled correct");
            response.sendRedirect("/reservation");
        }
        User client = (User) session.getAttribute("client");
        Reservation reservation = (Reservation) session.getAttribute("reservation");
        reservation.setClientLogin(client.getLogin());

        Map<Integer, Integer> reservedRoomTypes = getReservedRoomTypes(json);
        ReservationService reservationService = ServiceFactory.getInstance().getService(ReservationService.class);
        reservation = reservationService.reservation(reservation, reservedRoomTypes);
        if(reservation == null) {
            session.setAttribute("error", "sorry, we do not have enough rooms on the selected dates.");
            response.sendRedirect("/reservation");
            return;
        }
        setReservedRooms(reservation);
        session.setAttribute("reservation", reservation);
        response.sendRedirect("/bill");
    }

    private void setReservedRooms(Reservation reservation){
        ReservedRoomService reservedRoomService = ServiceFactory.getInstance().getService(ReservedRoomService.class);
        List<ReservedRoom> reservedRooms = reservedRoomService.getReservedRooms(reservation.getReservationId());
        reservation.setReservedRooms(reservedRooms);
    }

    private String getJson(HttpServletRequest request) throws IOException {
        StringBuffer json = new StringBuffer();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        return json.toString().replace("&confirm=confirm+reservation", "");
    }

    private Map<Integer, Integer> getReservedRoomTypes(String json){
        String[] jsonArray = json.split("&");
        Map<Integer, Integer> reservedRoomTypes = new HashMap<>();
        String[] entry;
        for(String s : jsonArray) {
            entry = s.split("=");
            reservedRoomTypes.put(Integer.valueOf(entry[0]), Integer.valueOf(entry[1]));
        }
        return reservedRoomTypes;
    }
}