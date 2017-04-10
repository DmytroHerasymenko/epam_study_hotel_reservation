package ua.study.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.validation.Validator;
import ua.study.domain.User;
import ua.study.service.LoginService;
import ua.study.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dima on 31.03.17.
 */
public class LoginHandlerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginHandlerCommand.class.getName());
    private final Validator validator;

    public LoginHandlerCommand(Validator validator){
        this.validator = validator;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);

        if(validator.isLoginIllegal(login)) {
            session.setAttribute("error", "login incorrect");
            responseRedirect(response, "./login");
            return;
        }
        if (validator.isPasswordIllegal(password)) {
            session.setAttribute("error", "password incorrect");
            responseRedirect(response, "./login");
            return;
        }
        LoginService loginService = ServiceFactory.getInstance().getLoginService();
        User client = loginService.loginAndPasswordVerify(login, password);
        if(client == null){
            session.setAttribute("error", "wrong login or password");
            responseRedirect(response, "./login");
        } else {
            session.setAttribute("login", client.getLogin());
            responseRedirect(response, "./reservation");
        }
    }

    private void responseRedirect(HttpServletResponse response, String url){
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
