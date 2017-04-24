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
            request.setAttribute("error", "login or password incorrect");
            request.getRequestDispatcher("/WEB-INF/jsp/sign_in.jsp").include(request, response);
            return;
        }
        UserService userService = ServiceFactory.getInstance().getService("UserService", UserService.class);
        User client = userService.loginAndPasswordVerify(login, password);
        if(client == null){
            request.setAttribute("error", "wrong login or password");
            request.getRequestDispatcher("/WEB-INF/jsp/sign_in.jsp").include(request, response);
            return;
        }
        session.setAttribute("client", client);
        request.getRequestDispatcher("/WEB-INF/jsp/check_dates.jsp").include(request, response);
    }
}