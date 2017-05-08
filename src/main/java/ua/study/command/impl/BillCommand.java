package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.domain.Reservation;
import ua.study.domain.RoomType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class BillCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if(session.getAttribute("reservedRoomTypes") == null){
            session.setAttribute("error", "error.filled_correct");
            response.sendRedirect("/reservation");
            return;
        }

        request.setAttribute("totalPrice", getTotalPrice(session));
        request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp").include(request, response);
    }

    private double getTotalPrice(HttpSession session){
        Map<RoomType, Integer> reservedRoomTypes = (Map<RoomType, Integer>) session.getAttribute("reservedRoomTypes");
        Reservation reservation = (Reservation) session.getAttribute("reservation");
        double stayingPeriod = ChronoUnit.DAYS.between(reservation.getArrivingDate(), reservation.getDepartureDate());

        final double[] totalPrice = {0};
        reservedRoomTypes.forEach((key,value) -> totalPrice[0] += key.getPrice() * value);

        return totalPrice[0] * stayingPeriod;
    }
}
