package ua.study.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.validation.Validator;
import ua.study.service.RegistrationService;
import ua.study.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dima on 30.03.17.
 */
public class RegistrationHandlerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationHandlerCommand.class.getName());
    private final Validator validator;

    public RegistrationHandlerCommand(Validator validator){
        this.validator = validator;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(validator.isNameIllegal(name) || validator.isLoginIllegal(login)
                || validator.isPasswordIllegal(password)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
            request.setAttribute("error", "all fields should be filled correct");
            requestForward(requestDispatcher, request, response);
            return;
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        RegistrationService service = factory.getRegistrationService();
        boolean isSuccess = service.registration(name, login, password);
        if(isSuccess){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            requestForward(requestDispatcher, request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
            request.setAttribute("error", "login is not unique");
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