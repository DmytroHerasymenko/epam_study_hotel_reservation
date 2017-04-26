package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.dao.impl.executor.ResultHandler;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;
import ua.study.domain.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class ReservedRoomDao extends AbstractDao<ReservedRoom> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(ReservedRoomDao.class.getName());

    public ReservedRoomDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.res_room"));
    }

    public boolean insert(Reservation reservation, List<ReservedRoom> reservedRooms) {
        return getExecutor().insertReservedRoom(reservation, reservedRooms, properties.getProperty("insert.res_room"));
    }

    public List<ReservedRoom> get(Reservation domain){
        String query = properties.getProperty("get.res_room");
        return (List) getExecutor().getReservedRoom(query, domain, new ResultHandlerReservedRoom());
    }

    public List<ReservedRoom> getUserReservedRooms(User domain){
        String query = properties.getProperty("get.user_res_room");
        return (List) getExecutor().getByLogin(query, domain, new ResultHandlerReservedRoom());
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private class ResultHandlerReservedRoom implements ResultHandler {
        public List<ReservedRoom> handle(ResultSet result) throws SQLException {
            List<ReservedRoom> reservedRooms = new ArrayList<>();
            if(!(result.next())) return null;
            do {
                ReservedRoom reservedRoom = new ReservedRoom();
                reservedRoom.setReservedRoomId(result.getLong(1));
                reservedRoom.setReservationId(result.getLong(2));
                reservedRoom.setRoomTypeId(result.getInt(3));
                reservedRoom.setRoomId(result.getInt(4));
                reservedRooms.add(reservedRoom);
            } while (result.next());
            return reservedRooms;
        }
    }
}