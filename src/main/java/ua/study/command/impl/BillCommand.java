package ua.study.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.Bill;
import ua.study.domain.ReservedRoom;
import ua.study.domain.RoomType;
import ua.study.service.BillService;
import ua.study.service.ReservationService;
import ua.study.service.ReservedRoomService;
import ua.study.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by dima on 09.04.17.
 */
public class BillCommand implements Command {
    private final Properties properties = new Properties();
    private static final Logger LOGGER = LogManager.getLogger(ReservationCommand.class.getName());
    private final Validator validator;

    public BillCommand(Validator validator){
        this.validator = validator;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        init();
        HttpSession session = request.getSession();
        if(!validator.isSessionValid(session)){
            try {
                response.sendRedirect("./login");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            ServiceFactory factory = ServiceFactory.getInstance();
            BillService billService = factory.getBillService();
            ReservedRoomService reservedRoomService = factory.getReservedRoomService();
            ReservationService reservationService = factory.getReservationService();
            List<RoomType> roomTypes = reservationService.getAllRoomTypes();
            Long reservationId = (Long) session.getAttribute("reservationId");

            List<ReservedRoom> reservedRooms = reservedRoomService.getReservedRooms(reservationId);
            Bill bill = billService.bill(reservationId);
            request.setAttribute("totalPrice", bill.getTotalPrice());
            request.setAttribute("reservedRooms", reservedRooms);
            request.setAttribute("roomTypes", roomTypes);
            RequestDispatcher dispatcher = request.getRequestDispatcher(properties.getProperty("req.bill"));
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                LOGGER.error(e.getMessage());
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/req.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
