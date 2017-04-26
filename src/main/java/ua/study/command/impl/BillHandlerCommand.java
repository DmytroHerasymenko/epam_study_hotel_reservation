package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.Bill;
import ua.study.domain.Reservation;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dima on 20.04.17.
 */
public class BillHandlerCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        BillService billService = ServiceFactory.getInstance().getService("BillService", BillService.class);
        Bill bill = new Bill();
        Reservation reservation = (Reservation) session.getAttribute("reservation");
        double totalPrice = (double) session.getAttribute("totalPrice");
        bill.setReservationId(reservation.getReservationId());
        bill.setTotalPrice(totalPrice);
        billService.bill(bill);
        request.setAttribute("reservation", reservation);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("reservedRoomTypes", session.getAttribute("reservedRoomTypes"));
        request.setAttribute("rooms", session.getAttribute("rooms"));
        session.removeAttribute("reservation");
        session.removeAttribute("totalPrice");
        session.removeAttribute("reservedRoomTypes");
        session.removeAttribute("rooms");
        request.getRequestDispatcher("/WEB-INF/jsp/confirmation.jsp").include(request, response);
    }
}
