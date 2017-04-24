package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.RoomType;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class BillCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoomTypeService roomTypeService =
                ServiceFactory.getInstance().getService("RoomTypeService", RoomTypeService.class);
        List<RoomType> roomTypes = roomTypeService.getRoomTypes();
        Map<RoomType, Integer> reservedRoomTypes = new HashMap<>();
        for(RoomType rt : roomTypes){
            reservedRoomTypes.put(rt, 0);
        }
        request.setAttribute("reservedRoomTypes", reservedRoomTypes);
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
    }
}
