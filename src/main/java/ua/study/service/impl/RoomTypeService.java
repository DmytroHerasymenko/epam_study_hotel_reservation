package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.RoomTypeDao;
import ua.study.domain.Reservation;
import ua.study.domain.RoomType;
import ua.study.service.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dima on 22.04.17.
 */
public class RoomTypeService implements Service{

    public Map<RoomType, Integer> getFreeRoomTypes(Reservation reservation){
        RoomTypeDao roomTypeDao = DaoFactory.getInstance().getDao(RoomTypeDao.class);
        return roomTypeDao.getFreeRoomTypes(reservation);
    }

    public List<RoomType> getRoomTypes(){
        RoomTypeDao roomTypeDao = DaoFactory.getInstance().getDao(RoomTypeDao.class);
        return roomTypeDao.get();
    }

}
