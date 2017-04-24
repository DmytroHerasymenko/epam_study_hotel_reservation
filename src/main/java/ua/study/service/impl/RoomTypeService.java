package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.RoomTypeDao;
import ua.study.domain.Reservation;
import ua.study.domain.RoomType;
import ua.study.service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by dima on 22.04.17.
 */
public class RoomTypeService implements Service{

    public List<RoomType> getRoomTypes(){
        RoomTypeDao roomTypeDao = DaoFactory.getInstance().getDao("RoomTypeDao", RoomTypeDao.class);
        return roomTypeDao.get();
    }

    public Map<RoomType, Integer> getFreeRoomTypes(LocalDate arrive, LocalDate departure){
        RoomTypeDao roomTypeDao = DaoFactory.getInstance().getDao("RoomTypeDao", RoomTypeDao.class);
        Reservation reservation = new Reservation();
        reservation.setArrivingDate(arrive);
        reservation.setDepartureDate(departure);
        return roomTypeDao.getFreeRoomTypes(reservation);
    }
}
