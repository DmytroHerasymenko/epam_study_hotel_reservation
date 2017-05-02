package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.User;
import ua.study.service.Service;

import java.util.List;
/**
 * Created by dima on 20.04.17.
 */
public class ReservedRoomService implements Service {

    public List<ReservedRoom> getReservedRooms(Long reservationId) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        ReservedRoomDao reservedRoomDao = DaoFactory.getInstance().getDao(ReservedRoomDao.class);
        return reservedRoomDao.get(reservation);
    }

    public List<ReservedRoom> getUserReservedRooms(String login) {
        User user = new User();
        user.setLogin(login);
        ReservedRoomDao reservedRoomDao =
                DaoFactory.getInstance().getDao(ReservedRoomDao.class);
        return reservedRoomDao.getUserReservedRooms(user);
    }
}
