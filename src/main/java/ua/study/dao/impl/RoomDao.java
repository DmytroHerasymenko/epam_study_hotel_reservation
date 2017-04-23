package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Reservation;
import ua.study.domain.Room;
import ua.study.domain.RoomType;

import java.io.IOException;
import java.util.*;

/**
 * Created by dima on 28.03.17.
 */
public class RoomDao extends AbstractDao<Room> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(RoomDao.class.getName());

    public RoomDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.room"));
    }

    private boolean insert(){
        return getExecutor().executorUpdate(properties.getProperty("insert.room"));
    }

    public List<Room> get(Reservation domain){
        String query = "SELECT COUNT(room_id), room_type_id FROM rooms GROUP BY room_type_id" +
                " WHERE room_number NOT IN (SELECT room_number FROM " +
                "reserved_rooms rr JOIN reservations r ON r.reservation_id = rr.reservation_id WHERE " +
                "(arriving_date <= ? AND departure_date > ?) OR " +
                "(arriving_date < ? AND departure_date >= ?) OR " +
                "(? <= arriving_date AND ? > arriving_date))";
        List<Room> rooms = new ArrayList<>();
        getExecutor().getFreeRooms(query, domain, result -> {
            if(!(result.next())) return null;
            do {
                Room room = new Room();
                room.setRoomNumber(result.getInt(1));
                room.setRoomTypeId(result.getInt(2));
                room.setBalcony(result.getBoolean(3));
                rooms.add(room);
            } while (result.next());
            return rooms;
        });
        System.out.println(rooms.get(0).getRoomNumber());
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
