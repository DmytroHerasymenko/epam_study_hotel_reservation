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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationHandlerCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String json = getJson(request);

        if(!UtilFactory.getInstance().getValidator().isReservedRoomsQuantityValid(json)){
            session.setAttribute("error", "error.choose_room");
            response.sendRedirect("/reservation");
            return;
        }

        Map<RoomType, Integer> reservedRoomTypes = getReservedRoomTypes(json);
        session.setAttribute("reservedRoomTypes", reservedRoomTypes);

        response.sendRedirect("/bill");
    }

    private String getJson(HttpServletRequest request) throws IOException {
        StringBuffer json = new StringBuffer();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            json.append(line);
        return json.toString().replace("&confirm=continue", "");
    }

    private Map<RoomType, Integer> getReservedRoomTypes(String json){
        String[] jsonArray = json.split("&");
        Map<RoomType, Integer> reservedRoomTypes = new HashMap<>();

        Arrays.stream(jsonArray).forEach(entry -> setRoomTypeById(reservedRoomTypes, entry));

        return reservedRoomTypes;
    }

    private void setRoomTypeById(Map<RoomType, Integer> reservedRoomTypes, String jsonMapEntry){
        RoomTypeService roomTypeService = ServiceFactory.getInstance().getService(RoomTypeService.class);
        List<RoomType> roomTypes = roomTypeService.getRoomTypes();

        String[] entry = jsonMapEntry.split("=");

        RoomType roomTypeById = roomTypes.stream()
                .filter(roomType -> roomType.getRoomTypeId() == Integer.parseInt(entry[0]))
                .findFirst().get();

        reservedRoomTypes.put(roomTypeById, Integer.valueOf(entry[1]));
    }
}