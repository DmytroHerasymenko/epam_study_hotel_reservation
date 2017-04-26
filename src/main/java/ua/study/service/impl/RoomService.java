package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.RoomDao;
import ua.study.domain.Room;
import ua.study.service.Service;

import java.util.List;

/**
 * Created by dima on 26.04.17.
 */
public class RoomService implements Service {

    public List<Room> getRooms(){
        RoomDao roomDao = DaoFactory.getInstance().getDao("RoomDao", RoomDao.class);
        return roomDao.get();
    }
}
