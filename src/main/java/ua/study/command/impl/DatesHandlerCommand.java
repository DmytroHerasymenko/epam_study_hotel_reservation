package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.util.UtilFactory;
import ua.study.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by dima on 03.04.17.
 */
public class DatesHandlerCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String arriveDate = request.getParameter("arriveDatepicker");
        String departureDate = request.getParameter("departureDatepicker");

        if (!UtilFactory.getInstance().getValidator().isReservationDatesValid(arriveDate, departureDate)) {
            session.setAttribute("error", "error.dates_correct");
            response.sendRedirect("/dates");
            return;
        }

        Dates dates = getDates(arriveDate, departureDate);
        session.setAttribute("dates", dates);
        response.sendRedirect("/reservation");
    }

    private Dates getDates(String arriveDate, String departureDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Dates dates = new Dates();
        dates.setArrivingDate(LocalDate.parse(arriveDate, formatter));
        dates.setDepartureDate(LocalDate.parse(departureDate, formatter));
        return dates;
    }
}