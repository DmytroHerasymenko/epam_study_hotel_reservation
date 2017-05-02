package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.Reservation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dima on 20.04.17.
 */
public class BillCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session.getAttribute("reservation") == null
                || ((Reservation) session.getAttribute("reservation")).getTotalPrice() == 0){
            session.setAttribute("error", "all fields should be filled correct");
            response.sendRedirect("/dates");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
    }
}
