package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.Bill;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dima on 20.04.17.
 */
public class BillHandlerCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BillService billService = ServiceFactory.getInstance().getService("BillService", BillService.class);
        Bill bill = new Bill();
        bill.setReservationId((Long) request.getSession().getAttribute("reservationId"));
        bill.setTotalPrice((Integer) request.getSession().getAttribute("totalPrice"));
        boolean isSuccess = billService.bill(bill);
        if(!isSuccess){
            request.setAttribute("error", "try one more time");
            request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/confirmation.jsp").include(request, response);
    }
}
