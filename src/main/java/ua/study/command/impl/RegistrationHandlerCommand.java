package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.util.UtilFactory;
import ua.study.command.util.validation.Validator;
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

        Validator validator = UtilFactory.getInstance().getValidator();
        if(!validator.isNameValid(name) || !validator.isLoginValid(login) || !validator.isPasswordValid(password)){
            request.getSession().setAttribute("error", "error.filled_correct");
            response.sendRedirect("/registration");
            return;
        }

        UserService userService = ServiceFactory.getInstance().getService(UserService.class);
        boolean isSuccess = userService.registration(name, login, password);
        if(!isSuccess){
            request.getSession().setAttribute("error", "error.login_not_unique");
            response.sendRedirect("/registration");
            return;
        }
        response.sendRedirect("/sign_in");
    }
}