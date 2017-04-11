package ua.study.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.Command;
import ua.study.command.validation.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dima on 31.03.17.
 */
public class ReservationCommand implements Command {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(ReservationCommand.class.getName());
    private final Validator validator;

    public ReservationCommand(Validator validator){
        this.validator = validator;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        init();
        HttpSession session = request.getSession();
        if(validator.isSessionValid(session)){
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(properties.getProperty("req.reserv"));
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

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/req.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
