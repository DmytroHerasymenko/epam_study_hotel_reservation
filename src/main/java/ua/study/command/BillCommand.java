package ua.study.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.validation.Validator;
import ua.study.domain.Bill;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.User;
import ua.study.service.BillService;
import ua.study.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by dima on 09.04.17.
 */
public class BillCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ReservationCommand.class.getName());
    private final Validator validator;

    public BillCommand(Validator validator){
        this.validator = validator;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(!validator.isSessionValid(session)){
            try {
                response.sendRedirect("./login");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            ServiceFactory factory = ServiceFactory.getInstance();
            BillService service = factory.getBillService();

            Bill bill = service.bill();
            User client = service.getClient();
            Reservation reservation = service.getReservation();
            List<ReservedRoom> reservedRooms = service.getReservedRooms();




            /*RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                LOGGER.error(e.getMessage());
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }  */
        }




        



    }
}
