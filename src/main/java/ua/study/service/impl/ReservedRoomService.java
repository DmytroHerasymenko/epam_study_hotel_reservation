package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.ReservedRoomDao;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.service.Service;

import java.util.List;

/**
 * Created by dima on 20.04.17.
 */
public class ReservedRoomService implements Service {

    public List<ReservedRoom> getUserReservedRooms(List<Reservation> reservations) {
        ReservedRoomDao reservedRoomDao = DaoFactory.getInstance().getDao(ReservedRoomDao.class);
        Long[] reservationsId = reservations.stream()
                .map(reservation -> reservation.getReservationId())
                .toArray(Long[]::new);
        return reservedRoomDao.getUserReservedRooms(reservationsId);
    }
}
