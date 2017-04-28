package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
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
        String arriveDate = request.getParameter("arriveDatepicker");
        String departureDate = request.getParameter("departureDatepicker");
        HttpSession session = request.getSession(false);

        if (!Validator.getInstance().isReservationDatesValid(arriveDate, departureDate)) {
            session.setAttribute("error", "all dates fields should be filled correct");
            response.sendRedirect("/dates");
            return;
        }

        Reservation reservation = getReservationDates(arriveDate, departureDate);
        session.setAttribute("reservation", reservation);
        response.sendRedirect("/reservation");
    }

    private Reservation getReservationDates(String arriveDate, String departureDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Reservation reservation = new Reservation();
        reservation.setArrivingDate(LocalDate.parse(arriveDate, formatter));
        reservation.setDepartureDate(LocalDate.parse(departureDate, formatter));
        return reservation;
    }
}