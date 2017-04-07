package ua.study.epam.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.epam.command.validation.Validator;
import ua.study.epam.service.ReservationService;
import ua.study.epam.service.ServiceFactory;

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
    private static final Logger LOGGER = LogManager.getLogger(ReservationHandlerCommand.class);
    private Validator validator;

    public ReservationHandlerCommand(Validator validator){
        this.validator = validator;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

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

        HttpSession session = request.getSession();

        if(!validator.isSessionValid(session)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            request.setAttribute("error", "session closed");
            requestForward(requestDispatcher, request, response);
            return;
        }
        ServiceFactory factory = ServiceFactory.getInstance();
        ReservationService service = factory.getReservationService();
        boolean isSuccess = service.reservation(String.valueOf(session.getAttribute("login")),
                arrive, departure, reservedRooms);

        if(isSuccess){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp");
            requestForward(requestDispatcher, request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp");
            request.setAttribute("error", "reservation cancelled, try one more time");
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
