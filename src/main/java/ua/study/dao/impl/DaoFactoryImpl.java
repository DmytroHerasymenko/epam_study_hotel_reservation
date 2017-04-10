package ua.study.dao.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.GenericDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class DaoFactoryImpl implements DaoFactory {

    private static DaoFactory instance;

    private final Map<String, GenericDao> daoMap = new HashMap<>();

    private DaoFactoryImpl(){
        init();
    }

    public static DaoFactory getInstance(){
        if(instance == null){
            instance = new DaoFactoryImpl();
        }
        return instance;
    }

    @Override
    public <T> T getDao(String name, Class<T> classDto) {
        GenericDao dao = daoMap.get(name);
        if(dao == null) throw new IllegalArgumentException("dao not found");
        return classDto.cast(dao);
    }

    private void init(){
        daoMap.put("BillDao", new BillDaoImpl());
        daoMap.put("ReservationDao", new ReservationDaoImpl());
        daoMap.put("ReservedRoomDao", new ReservedRoomDaoImpl());
        daoMap.put("RoomDao", new RoomDaoImpl());
        daoMap.put("RoomTypeDao", new RoomTypeDaoImpl());
        daoMap.put("UserDao", new UserDaoImpl());
    }
}