package ua.study.command.impl;

import ua.study.command.Command;

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
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        session.removeAttribute("totalPrice");

        if(totalPrice == null){
            session.setAttribute("error", "error.filled_correct");
            response.sendRedirect("/reservation");
            return;
        }

        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
    }
}
