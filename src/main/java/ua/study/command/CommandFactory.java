package ua.study.command;

import ua.study.command.impl.*;
import ua.study.command.impl.info.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 18.04.17.
 */
public class CommandFactory {

    private static final CommandFactory instance = new CommandFactory();

    private final Map<String, Command> commands = new HashMap<>();

    private CommandFactory(){
        init();
    }

    public static CommandFactory getInstance(){
        return instance;
    }

    public Command getCommand(HttpServletRequest request){
        return commands.get(request.getRequestURI());
    }

    private void init(){
        commands.put("/registration", new RegistrationCommand());
        commands.put("/registration_handler", new RegistrationHandlerCommand());
        commands.put("/sign_in", new SignInCommand());
        commands.put("/sign_in_handler", new SignInHandlerCommand());
        commands.put("/dates", new DatesCommand());
        commands.put("/dates_handler", new DatesHandlerCommand());
        commands.put("/reservation", new ReservationCommand());
        commands.put("/reservation_handler", new ReservationHandlerCommand());
        commands.put("/bill", new BillCommand());
        commands.put("/bill_handler", new BillHandlerCommand());
        commands.put("/my_reservations", new MyReservationsCommand());
        commands.put("/confirmation", new ConfirmationCommand());
        commands.put("/index", new IndexCommand());
        commands.put("/news", new NewsCommand());
        commands.put("/about", new AboutCommand());
        commands.put("/contacts", new ContactsCommand());
    }
}
