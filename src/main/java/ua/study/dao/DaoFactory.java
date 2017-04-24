package ua.study.dao;

import ua.study.dao.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class DaoFactory {

    private static final DaoFactory instance = new DaoFactory();

    private final Map<String, GenericDao> daoMap = new HashMap<>();

    private DaoFactory(){
        init();
    }

    public static DaoFactory getInstance(){
        return instance;
    }

    public <T> T getDao(String name, Class<T> classDto) {
        GenericDao dao = daoMap.get(name);
        if(dao == null) throw new IllegalArgumentException("dao not found");
        return classDto.cast(dao);
    }

    private void init(){
        daoMap.put("BillDao", new BillDao());
        daoMap.put("ReservationDao", new ReservationDao());
        daoMap.put("ReservedRoomDao", new ReservedRoomDao());
        daoMap.put("RoomDao", new RoomDao());
        daoMap.put("RoomTypeDao", new RoomTypeDao());
        daoMap.put("UserDao", new UserDao());
    }
}