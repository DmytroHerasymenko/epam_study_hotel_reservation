package ua.study.epam.dao.postgres;

import ua.study.epam.dao.DaoFactory;
import ua.study.epam.dao.GenericDao;
import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class DaoFactoryPostgres implements DaoFactory {

    private static DaoFactory instance;

    private final Map<String, GenericDao> daoMap = new HashMap<>();

    private DaoFactoryPostgres(ConnectionProxy connectionProxy){
        init(connectionProxy);
    }

    public static DaoFactory getInstance(ConnectionProxy connectionProxy){
        if(instance == null){
            instance = new DaoFactoryPostgres(connectionProxy);
        }
        return instance;
    }

    @Override
    public <T> T getDao(String name, Class<T> classDto) {
        GenericDao dao = daoMap.get(name);
        if(dao == null) throw new IllegalArgumentException("dao not found");
        return classDto.cast(dao);
    }

    private void init(ConnectionProxy connectionProxy){
        daoMap.put("BillDao", new BillDaoPostgres(connectionProxy));
        daoMap.put("ReservationDao", new ReservationDaoPostgres(connectionProxy));
        daoMap.put("ReservedRoomDao", new ReservedRoomDaoPostgres(connectionProxy));
        daoMap.put("RoomDao", new RoomDaoPostgres(connectionProxy));
        daoMap.put("RoomTypeDao", new RoomTypeDaoPostgres(connectionProxy));
        daoMap.put("UserDao", new UserDaoPostgres(connectionProxy));
    }
}