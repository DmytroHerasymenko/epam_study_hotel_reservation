package ua.study.dao;

import ua.study.dao.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class DaoFactory {

    private static final DaoFactory instance = new DaoFactory();

    private final Map<Class<? extends GenericDao>, GenericDao> daoMap = new HashMap<>();

    private DaoFactory(){
        init();
    }

    public static DaoFactory getInstance(){
        return instance;
    }

    public <T> T getDao(Class<T> classDto) {
        GenericDao dao = daoMap.get(classDto);
        if(dao == null) throw new IllegalArgumentException("dao not found");
        return classDto.cast(dao);
    }

    private void init(){
        daoMap.put(BillDao.class, new BillDao());
        daoMap.put(ReservationDao.class, new ReservationDao());
        daoMap.put(ReservedRoomDao.class, new ReservedRoomDao());
        daoMap.put(RoomDao.class, new RoomDao());
        daoMap.put(RoomTypeDao.class, new RoomTypeDao());
        daoMap.put(UserDao.class, new UserDao());
    }
}