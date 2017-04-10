package ua.study.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.domain.Reservation;
import ua.study.command.validation.Validator;
import ua.study.domain.enums.ReservationStatus;
import ua.study.service.ReservationService;
import ua.study.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
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
public class ReservationHandlerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ReservationHandlerCommand.class.getName());
    private final Validator validator;

    public ReservationHandlerCommand(Validator validator){
        this.validator = validator;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(!validator.isSessionValid(session)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            request.setAttribute("error", "session closed");
            requestForward(requestDispatcher, request, response);
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int[] reservedRooms = new int[6];
        LocalDate arrive;
        LocalDate departure;
        try {
            reservedRooms[0] = Integer.parseInt(request.getParameter("st_single"));
            reservedRooms[1] = Integer.parseInt(request.getParameter("st_double"));
            reservedRooms[2] = Integer.parseInt(request.getParameter("st_twin"));
            reservedRooms[3] = Integer.parseInt(request.getParameter("suite_double"));
            reservedRooms[4] = Integer.parseInt(request.getParameter("suite_twin"));
            reservedRooms[5] = Integer.parseInt(request.getParameter("deluxe_double"));
            arrive = LocalDate.parse(request.getParameter("arriveDatepicker"), formatter);
            departure = LocalDate.parse(request.getParameter("departureDatepicker"), formatter);
        } catch (IllegalArgumentException e){
            LOGGER.error(e.getMessage());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp");
            request.setAttribute("error", "all fields should be filled correct");
            requestForward(requestDispatcher, request, response);
            return;
        }
        if(validator.isReservationDataIllegal(arrive, departure, reservedRooms)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp");
            request.setAttribute("error", "all fields should be filled correct");
            requestForward(requestDispatcher, request, response);
            return;
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        ReservationService service = factory.getReservationService();
        Reservation reservation = service.reservation(String.valueOf(session.getAttribute("login")),
                arrive, departure, reservedRooms);

        if(reservation == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp");
            request.setAttribute("error", "reservation cancelled, try one more time");
            requestForward(requestDispatcher, request, response);
            return;
        }
        if(reservation.getStatus() == ReservationStatus.CONFIRMED) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp");
            request.setAttribute("arrive", arrive);
            request.setAttribute("departure", departure);
            request.setAttribute("reservationId", reservation.getReservationId());
            requestForward(requestDispatcher, request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp");
            request.setAttribute("error", "sorry, we do not have enough rooms on the selected dates. " +
                    "Try to choose another room category.");
            requestForward(requestDispatcher, request, response);
        }
    }

    private void requestForward(RequestDispatcher requestDispatcher,
                                HttpServletRequest request, HttpServletResponse response){
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
