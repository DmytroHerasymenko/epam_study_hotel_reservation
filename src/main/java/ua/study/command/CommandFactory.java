package ua.study.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dima on 30.03.17.
 */
public interface CommandFactory {

    static CommandFactory getInstance(){
        return CommandFactoryImpl.getInstance();
    }

    <T> T getCommand(HttpServletRequest request);
}