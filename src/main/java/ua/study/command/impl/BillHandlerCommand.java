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
        Reservation reservation = (Reservation) session.getAttribute("reservation");

        BillService billService = ServiceFactory.getInstance().getService(BillService.class);
        Bill bill = new Bill();
        bill.setReservationId(reservation.getReservationId());
        bill.setTotalPrice(reservation.getTotalPrice());
        billService.bill(bill);

        request.setAttribute("reservation", reservation);
        session.removeAttribute("reservation");
        request.getRequestDispatcher("/WEB-INF/jsp/confirmation.jsp").include(request, response);
    }
}
