package ua.study.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dima on 30.03.17.
 */
public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response);
}
