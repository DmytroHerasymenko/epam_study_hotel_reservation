package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class RoomDao extends AbstractDao<Room> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(BillDao.class);

    public RoomDao(){
        init();
    }

    public List<Room> get(){
        String getRooms = properties.getProperty("get.room");
        List<Room> rooms = new ArrayList<>();
        getExecutor().executorQuery(getRooms, result -> {
            if(!(result.next())) return null;
            do {
                Room room = new Room();
                room.setRoomNumber(result.getInt(1));
                room.setRoomTypeId(result.getLong(2));
                room.setBalcony(result.getBoolean(3));
                rooms.add(room);
            } while (result.next());
            return rooms;
        });
        return rooms;
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/dao.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Override
    public void create() {
        String create = properties.getProperty("create.room");
        getExecutor().executorUpdate(create);
    }

    private boolean insert(){
        String insert = properties.getProperty("insert.room");
        return getExecutor().roomInsert(insert);
    }
}
