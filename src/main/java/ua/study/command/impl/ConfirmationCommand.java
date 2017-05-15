package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.Reservation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dima on 12.05.17.
 */
public class ConfirmationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Reservation reservation = (Reservation) session.getAttribute("reservation");
        session.removeAttribute("reservation");

        if(reservation == null || reservation.getReservationId() == 0){
            session.setAttribute("error", "error.filled_correct");
            response.sendRedirect("/reservation");
            return;
        }

        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("/WEB-INF/jsp/confirmation.jsp").include(request, response);
    }
}
