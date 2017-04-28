package ua.study.command.impl;

import ua.study.command.Command;
import ua.study.command.validation.Validator;
import ua.study.domain.User;
import ua.study.service.ServiceFactory;
import ua.study.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dima on 31.03.17.
 */
public class SignInHandlerCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if(!Validator.getInstance().isLoginValid(login) || !Validator.getInstance().isPasswordValid(password)) {
            session.setAttribute("error", "login or password incorrect");
            response.sendRedirect("/sign_in");
            return;
        }

        UserService userService = ServiceFactory.getInstance().getService("UserService", UserService.class);
        User client = userService.loginAndPasswordVerify(login, password);
        if(client == null){
            session.setAttribute("error", "wrong login or password");
            response.sendRedirect("/sign_in");
            return;
        }

        session.setAttribute("client", client);
        response.sendRedirect("/dates");
    }
}