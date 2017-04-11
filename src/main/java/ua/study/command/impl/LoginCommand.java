package ua.study.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dima on 31.03.17.
 */
public class LoginCommand implements Command {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        init();
        RequestDispatcher dispatcher = request.getRequestDispatcher(properties.getProperty("req.login"));
        try {
            dispatcher.forward(request, response);
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
