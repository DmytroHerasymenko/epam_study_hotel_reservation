package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservationDao;
import ua.study.dao.impl.executor.TransactionHelper;
import ua.study.domain.Reservation;
import ua.study.domain.RoomType;
import ua.study.domain.User;
import ua.study.service.Service;
import ua.study.service.ServiceFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 20.04.17.
 */
public class ReservationService implements Service {

    public Reservation reservation(String login, LocalDate arrive, LocalDate departure,
                                   Map<RoomType, Integer> reservedRoomTypes){
        ReservationDao reservationDao = DaoFactory.getInstance().getDao("ReservationDao", ReservationDao.class);
        ReservedRoomService reservedRoomService =
                ServiceFactory.getInstance().getService("ReservedRoomService", ReservedRoomService.class);
        UserService userService = ServiceFactory.getInstance().getService("UserService", UserService.class);
        User user = userService.getUserByLogin(login);


        Reservation reservation = new Reservation();
        reservation.setClientId(user.getUserId());
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);

        TransactionHelper.getInstance().beginTransaction();
        Long reservationId = (reservationDao.insert(reservation));
        /*if(reservationId == null){
            TransactionHelper.getInstance().rollbackTransaction();
            return null;
        }*/
        reservation.setReservationId(reservationId);
        boolean isSuccess = reservedRoomService.reservation(reservation, reservedRoomTypes);
        if(!isSuccess){
            TransactionHelper.getInstance().rollbackTransaction();
            return null;
        }
        TransactionHelper.getInstance().commitTransaction();
        return reservation;
    }

    public List<Reservation> getReservations(String login) {
        ReservationDao reservationDao = DaoFactory.getInstance().getDao("ReservationDao", ReservationDao.class);
        User user = new User();
        user.setLogin(login);
        return reservationDao.get(user);
    }
}
