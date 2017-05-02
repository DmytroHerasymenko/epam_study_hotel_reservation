package ua.study;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.*;

/**
 * Created by dima on 20.04.17.
 */
public class Main {
    public static void main(String[] args) {
        BillDao billDao = DaoFactory.getInstance().getDao(BillDao.class);
        ReservationDao reservationDao = DaoFactory.getInstance().getDao(ReservationDao.class);
        ReservedRoomDao reservedRoomDao = DaoFactory.getInstance().getDao(ReservedRoomDao.class);
        RoomDao roomDao = DaoFactory.getInstance().getDao(RoomDao.class);
        RoomTypeDao roomTypeDao = DaoFactory.getInstance().getDao(RoomTypeDao.class);
        UserDao userDao = DaoFactory.getInstance().getDao(UserDao.class);
        userDao.create();
        roomTypeDao.create();
        roomDao.create();
        reservationDao.create();
        billDao.create();
        reservedRoomDao.create();



    }
}
