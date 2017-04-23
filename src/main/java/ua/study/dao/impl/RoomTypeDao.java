package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Reservation;
import ua.study.domain.RoomType;
import ua.study.domain.enums.Bedspace;
import ua.study.domain.enums.RoomCategory;

import java.io.IOException;
import java.util.*;

/**
 * Created by dima on 28.03.17.
 */
public class RoomTypeDao extends AbstractDao<RoomType> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(RoomTypeDao.class.getName());

    public RoomTypeDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("category.room_type"));
        getExecutor().executorUpdate(properties.getProperty("bedspace.room_type"));
        getExecutor().executorUpdate(properties.getProperty("create.room_type"));
    }

    private boolean insert(){
        getExecutor().executorUpdate(properties.getProperty("st_single.room_type"));
        getExecutor().executorUpdate(properties.getProperty("st_double.room_type"));
        getExecutor().executorUpdate(properties.getProperty("st_twin.room_type"));
        getExecutor().executorUpdate(properties.getProperty("su_double.room_type"));
        getExecutor().executorUpdate(properties.getProperty("su_twin.room_type"));
        return getExecutor().executorUpdate(properties.getProperty("de_double.room_type"));
    }

    public List<RoomType> get(){
        String getRoomTypesSql = properties.getProperty("get.room_type");
        List<RoomType> roomTypes = new ArrayList<>();
        getExecutor().executorQuery(getRoomTypesSql, result -> {
            if(!(result.next())) return null;
            do {
                RoomType roomType = new RoomType();
                roomType.setRoomTypeId(result.getInt(1));
                roomType.setRoomCategory(RoomCategory.valueOf(result.getString(2)));
                roomType.setBedspace(Bedspace.valueOf(result.getString(3)));
                roomType.setPrice(result.getInt(4));
                roomTypes.add(roomType);
            } while(result.next());
            return roomTypes;
        });
        return roomTypes;
    }

    public Map<RoomType, Integer> getFreeRoomTypes(Reservation domain){
        String query = "SELECT COUNT(room_id), room_type_id FROM rooms " +
                " WHERE room_number NOT IN (SELECT room_number FROM " +
                "reserved_rooms rr JOIN reservations r ON r.reservation_id = rr.reservation_id WHERE " +
                "(arriving_date <= ? AND departure_date > ?) OR " +
                "(arriving_date < ? AND departure_date >= ?) OR " +
                "(? <= arriving_date AND ? > arriving_date)) GROUP BY room_type_id ORDER BY room_type_id";
        //List<Room> rooms = new ArrayList<>();
        Map<RoomType, Integer> freeRoomTypes = new LinkedHashMap<>();
        getExecutor().getFreeRooms(query, domain, result -> {
            if(!(result.next())) return null;
            do {
                //Room room = new Room();
                RoomType roomType = new RoomType();
                //room.setRoomNumber(result.getInt(1));
                roomType.setRoomTypeId(result.getInt(2));
                //room.setBalcony(result.getBoolean(3));
                freeRoomTypes.put(roomType, result.getInt(1));
            } while (result.next());
            return freeRoomTypes;
        });
        return freeRoomTypes;
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
