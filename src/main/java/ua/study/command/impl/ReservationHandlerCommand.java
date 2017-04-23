package ua.study.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.ReservationService;
import ua.study.service.impl.ReservedRoomService;
import ua.study.service.impl.RoomService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationHandlerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ReservationHandlerCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<RoomType, Integer> reservedRoomTypes = getReservedRooms(request);
        if(!Validator.getInstance().isReservedRoomsQuantityValid(reservedRoomTypes) ||
                !Validator.getInstance().isAnyRoomReserved(reservedRoomTypes)) {
            request.setAttribute("error", "all fields should be filled correct");
            request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
            return;
        }
        HttpSession session = request.getSession(false);
        ReservationService reservationService =
                ServiceFactory.getInstance().getService("ReservationService", ReservationService.class);
        String login = String.valueOf(((User) session.getAttribute("client")).getLogin());
        LocalDate arrive = (LocalDate) session.getAttribute("arrive");
        LocalDate departure = (LocalDate) session.getAttribute("departure");
        Reservation reservation = reservationService.reservation(login, arrive, departure, reservedRoomTypes);
        if(reservation == null) {
            request.setAttribute("error", "sorry, we do not have enough rooms on the selected dates.");
            RoomService roomService = ServiceFactory.getInstance().getService("RoomService", RoomService.class);
            List<Room> freeRooms = roomService.getFreeRooms(arrive, departure);
            request.setAttribute("freeRooms", freeRooms);
            request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").include(request, response);
            return;
        }
        request.getSession().setAttribute("reservationId", reservation.getReservationId());
        request.getSession().setAttribute("totalPrice", totalPrice(reservedRoomTypes));
        request.setAttribute("roomTypes", reservedRoomTypes);
        ReservedRoomService reservedRoomService =
                ServiceFactory.getInstance().getService("ReservedRoomService", ReservedRoomService.class);
        Long reservationId = (Long) request.getSession().getAttribute("reservationId");
        List<ReservedRoom> reservedRoomsList = reservedRoomService.getReservedRooms(reservationId);
        request.setAttribute("reservedRooms", reservedRoomsList);
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
        //request.getRequestDispatcher("/bill").include(request, response);
        //response.sendRedirect("./bill");
    }

    /*private Map<RoomType, Integer> getReservedRooms(HttpServletRequest request){
        Map<RoomType, Integer> reservedRooms;
        try {
            reservedRooms = (Map<RoomType, Integer>) request.getAttribute("reservedRoomss");
            return reservedRooms;
        } catch (IllegalArgumentException e){
            LOGGER.error(e.getMessage());
            request.setAttribute("error", "wrong rooms quantity");
            return null;
        }
    }*/

    private Map<RoomType, Integer> getReservedRooms(HttpServletRequest request){
        Map<RoomType, Integer> reservedRooms = new HashMap<>();
        try {
            System.out.println(request.getAttribute("STANDARD_SINGLE_KEY"));
            System.out.println(request.getAttribute("STANDARD_SINGLE"));
            reservedRooms.put((RoomType) request.getAttribute("STANDARD_SINGLE_KEY"),
                    Integer.parseInt(request.getParameter("STANDARD_SINGLE")));
            reservedRooms.put((RoomType) request.getAttribute("STANDARD_DOUBLE_KEY"),
                    Integer.parseInt(request.getParameter("STANDARD_DOUBLE")));
            reservedRooms.put((RoomType) request.getAttribute("STANDARD_TWIN_KEY"),
                    Integer.parseInt(request.getParameter("STANDARD_TWIN")));
            reservedRooms.put((RoomType) request.getAttribute("SUITE_DOUBLE_KEY"),
                    Integer.parseInt(request.getParameter("SUITE_DOUBLE")));
            reservedRooms.put((RoomType) request.getAttribute("SUITE_TWIN_KEY"),
                    Integer.parseInt(request.getParameter("SUITE_TWIN")));
            reservedRooms.put((RoomType) request.getAttribute("DELUXE_DOUBLE_KEY"),
                    Integer.parseInt(request.getParameter("DELUXE_DOUBLE")));
            return reservedRooms;
        } catch (IllegalArgumentException e){
            LOGGER.error(e.getMessage());
            request.setAttribute("error", "wrong rooms quantity");
            return null;
        }
    }

    private int totalPrice(Map<RoomType, Integer> reservedRoomTypes){
        int totalPrice = 0;
        for(Map.Entry<RoomType, Integer> entry : reservedRoomTypes.entrySet()){
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return totalPrice;
    }
}
