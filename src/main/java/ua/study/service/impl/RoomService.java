package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.RoomDao;
import ua.study.domain.Reservation;
import ua.study.domain.Room;
import ua.study.domain.RoomType;
import ua.study.service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 18.04.17.
 */
public class RoomService implements Service {
    public List<Room> getFreeRooms(LocalDate arrive, LocalDate departure) {
        RoomDao roomDao = DaoFactory.getInstance().getDao("RoomDao", RoomDao.class);
        Reservation reservation = new Reservation();
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
        return roomDao.get(reservation);
    }
}
