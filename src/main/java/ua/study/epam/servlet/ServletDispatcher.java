package ua.study.epam.servlet;

import ua.study.epam.command.Command;
import ua.study.epam.command.CommandFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dima on 30.03.17.
 */
public class ServletDispatcher extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        CommandFactory factory = CommandFactory.getInstance();
        Command command = factory.getCommand(request);
        command.execute(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest( request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest( request, response);
    }
}
