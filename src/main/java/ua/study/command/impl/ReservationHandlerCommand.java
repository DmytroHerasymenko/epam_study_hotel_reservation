package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.util.UtilFactory;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationHandlerCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Map<RoomType, String> reservedRoomQuantity = getReservedRoomsQuantity(request);

        if(!UtilFactory.getInstance().getValidator().isReservedRoomsQuantityValid(reservedRoomQuantity)){
            session.setAttribute("error", "error.choose_room");
            response.sendRedirect("/reservation");
            return;
        }

        Map<RoomType, Integer> reservedRoomTypes = getReservedRoomTypes(reservedRoomQuantity);
        session.setAttribute("reservedRoomTypes", reservedRoomTypes);

        response.sendRedirect("/bill");
    }

    private Map<RoomType, String> getReservedRoomsQuantity(HttpServletRequest request){
        RoomTypeService roomTypeService = ServiceFactory.getInstance().getService(RoomTypeService.class);
        List<RoomType> roomTypes = roomTypeService.getRoomTypes();
        Map<RoomType, String> reservedRoomsQuantity = new LinkedHashMap<>();

        roomTypes.forEach(roomType -> {
            String s = request.getParameter(String.valueOf(roomType.getRoomTypeId()));
            reservedRoomsQuantity.put(roomType, s != null ? s : "0");
        });
        return reservedRoomsQuantity;
    }

    private Map<RoomType, Integer> getReservedRoomTypes(Map<RoomType, String> reservedRoomQuantity){
        return reservedRoomQuantity.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Integer.parseInt(entry.getValue()),
                        (k,v)-> {throw new AssertionError();},
                        LinkedHashMap::new
                ));
    }
}