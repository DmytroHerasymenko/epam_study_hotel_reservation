package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.*;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomTypeService;

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
            session.setAttribute("error", "choose a room for reservation, please");
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
        for(String s : jsonArray) {
            getRoomTypeById(reservedRoomTypes, s);
        }
        return reservedRoomTypes;
    }

    private void getRoomTypeById(Map<RoomType, Integer> reservedRoomTypes, String jsonMapEntry){
        String[] entry = jsonMapEntry.split("=");
        RoomTypeService roomTypeService = ServiceFactory.getInstance().getService(RoomTypeService.class);
        List<RoomType> roomTypes = roomTypeService.getRoomTypes();
        roomTypes.forEach(roomType -> {
            if(Integer.parseInt(entry[0]) == roomType.getRoomTypeId())
                reservedRoomTypes.put(roomType, Integer.valueOf(entry[1]));
        });
    }
}