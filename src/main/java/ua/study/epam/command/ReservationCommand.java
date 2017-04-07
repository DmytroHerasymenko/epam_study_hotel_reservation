package ua.study.epam.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.epam.command.validation.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dima on 31.03.17.
 */
public class ReservationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ReservationCommand.class);
    private Validator validator;

    public ReservationCommand(Validator validator){
        this.validator = validator;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(validator.isSessionValid(session)){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                LOGGER.error(e.getMessage());
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            try {
                response.sendRedirect("./login");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
