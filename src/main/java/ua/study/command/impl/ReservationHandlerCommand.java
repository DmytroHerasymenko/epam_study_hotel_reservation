package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;
import ua.study.service.impl.RoomService;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
        LocalDate arrive = (LocalDate) session.getAttribute("arrive");
        LocalDate departure = (LocalDate) session.getAttribute("departure");
        Map<RoomType, Integer> reservedRoomTypes = getReservedRooms(request);
        if(reservedRoomTypes == null) {
            getFreeRooms(arrive, departure, request);
            request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
            return;
        }
        String login = String.valueOf(((User) session.getAttribute("client")).getLogin());
        ReservationService reservationService =
                ServiceFactory.getInstance().getService("ReservationService", ReservationService.class);
        Reservation reservation = reservationService.reservation(login, arrive, departure, reservedRoomTypes);
        if(reservation == null) {
            request.setAttribute("error", "sorry, we do not have enough rooms on the selected dates.");
            getFreeRooms(arrive, departure, request);
            request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
            return;
        }
        setReservation(reservation, arrive, departure);
        session.removeAttribute("arrive");
        session.removeAttribute("departure");
        session.setAttribute("rooms", getRooms());
        session.setAttribute("reservedRoomTypes", reservedRoomTypes);
        session.setAttribute("reservation", reservation);
        session.setAttribute("totalPrice", totalPrice(reservedRoomTypes));
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
    }

    private List<Room> getRooms(){
        RoomService roomService = ServiceFactory.getInstance().getService("RoomService", RoomService.class);
        return roomService.getRooms();
    }

    private void getFreeRooms(LocalDate arrive, LocalDate departure, HttpServletRequest request){
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);
        Map<RoomType, Integer> freeRoomTypes = roomTypeService.getFreeRoomTypes(arrive, departure);
        request.setAttribute("freeRooms", freeRoomTypes);
    }

    private Map<RoomType, Integer> getReservedRooms(HttpServletRequest request) throws IOException, ServletException {
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);
        List<RoomType> roomTypes = roomTypeService.getRoomTypes();
        List<String> reservedRoomsQuantity = setReservedRoomsQuantity(request, roomTypes);
        if(!Validator.getInstance().isReservedRoomsQuantityValid(reservedRoomsQuantity)) {
            request.setAttribute("error", "all fields should be filled correct");
            return null;
        }
        Map<RoomType, Integer> reservedRoomTypes = setReservedRoomTypes(roomTypes, reservedRoomsQuantity);
        return reservedRoomTypes;
    }

    private List<String> setReservedRoomsQuantity(HttpServletRequest request, List<RoomType> roomTypes){
        List<String> reservedRoomsQuantity = new ArrayList<>();
        String quantity;
        for(RoomType rt : roomTypes){
            quantity = request.getParameter(rt.getRoomCategory() + "_" + rt.getBedspace());
            if(quantity == null) quantity = "0";
            reservedRoomsQuantity.add(quantity);
        }
        return reservedRoomsQuantity;
    }

    private Map<RoomType, Integer> setReservedRoomTypes(List<RoomType> roomTypes, List<String> reservedRoomsQuantity){
        Map<RoomType, Integer> reservedRoomTypes = new HashMap<>();
        for(int i = 0; i < roomTypes.size(); i++){
            reservedRoomTypes.put(roomTypes.get(i), Integer.valueOf(reservedRoomsQuantity.get(i)));
        }
        return reservedRoomTypes;
    }

    private void setReservation(Reservation reservation, LocalDate arrive, LocalDate departure){
        ReservedRoomService reservedRoomService =
                ServiceFactory.getInstance().getService("ReservedRoomService", ReservedRoomService.class);
        List<ReservedRoom> reservedRooms = reservedRoomService.getReservedRooms(reservation.getReservationId());
        reservation.setReservedRooms(reservedRooms);
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
    }

    private double totalPrice(Map<RoomType, Integer> reservedRoomTypes){
        double totalPrice = 0;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            totalPrice += entry.getKey().getPrice() * (double) entry.getValue();
        }
        return totalPrice;
    }
}

/*StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {}
        Map<RoomType, Integer> result = new ObjectMapper().readValue(jb.toString(), HashMap.class);*/
