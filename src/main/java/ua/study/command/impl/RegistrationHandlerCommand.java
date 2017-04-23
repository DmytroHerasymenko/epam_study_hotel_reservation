package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dima on 30.03.17.
 */
public class RegistrationHandlerCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(!Validator.getInstance().isNameValid(name) || !Validator.getInstance().isLoginValid(login)
                || !Validator.getInstance().isPasswordValid(password)){
            request.setAttribute("error", "all fields should be filled correct");
            request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").include(request, response);
            return;
        }
        UserService userService = ServiceFactory.getInstance().getService("UserService", UserService.class);
        boolean isSuccess = userService.registration(name, login, password);
        if(!isSuccess){
            request.setAttribute("error", "login is not unique");
            request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").include(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/sign_in.jsp").include(request, response);
    }
}