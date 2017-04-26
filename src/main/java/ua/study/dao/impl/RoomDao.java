package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Room;

import java.io.IOException;
import java.util.*;

/**
 * Created by dima on 28.03.17.
 */
public class RoomDao extends AbstractDao {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(RoomDao.class.getName());

    public RoomDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.room"));
    }

    public List<Room> get(){
        List<Room> rooms = new ArrayList<>();
        getExecutor().executorQuery(properties.getProperty("get.room"), result -> {
            if(!(result.next())) return null;
            do {
                Room room = new Room();
                room.setRoomId(result.getInt(1));
                room.setRoomNumber(result.getInt(2));
                room.setRoomTypeId(result.getInt(3));
                room.setBalcony(result.getBoolean(4));
                rooms.add(room);
            } while (result.next());
            return rooms;
        });
        return rooms;
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
