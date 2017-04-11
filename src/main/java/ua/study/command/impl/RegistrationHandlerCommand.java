package ua.study.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.service.RegistrationService;
import ua.study.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dima on 30.03.17.
 */
public class RegistrationHandlerCommand implements Command {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(RegistrationHandlerCommand.class.getName());
    private final Validator validator;

    public RegistrationHandlerCommand(Validator validator){
        this.validator = validator;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        init();
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(validator.isNameIllegal(name) || validator.isLoginIllegal(login)
                || validator.isPasswordIllegal(password)){
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher(properties.getProperty("req.registr"));
            request.setAttribute("error", "all fields should be filled correct");
            requestForward(requestDispatcher, request, response);
            return;
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        RegistrationService service = factory.getRegistrationService();
        boolean isSuccess = service.registration(name, login, password);
        if(isSuccess){
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher(properties.getProperty("req.login"));
            requestForward(requestDispatcher, request, response);
        } else {
            RequestDispatcher requestDispatcher =
                    request.getRequestDispatcher(properties.getProperty("req.registr"));
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

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/req.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}