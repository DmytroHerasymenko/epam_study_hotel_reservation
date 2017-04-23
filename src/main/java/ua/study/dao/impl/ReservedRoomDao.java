package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;

import java.io.IOException;
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

    public boolean insert(List<ReservedRoom> reservedRooms) {
        String insert = "INSERT INTO reserved_rooms res (reservation_id, room_type_id, room_number) " +
                "VALUES (?,?, (SELECT room_number FROM rooms rooms WHERE res.room_type_id = rooms.room_type_id " +
                "AND rooms.room_number NOT IN (SELECT room_number FROM " +
                "reserved_rooms rr JOIN reservations r ON r.reservation_id = rr.reservation_id WHERE " +
                "(arriving_date <= ? AND departure_date > ?) OR " +
                "(arriving_date < ? AND departure_date >= ?) OR " +
                "(? <= arriving_date AND ? > arriving_date))))";
        return getExecutor().insertReservedRoom(reservedRooms, insert);
    }

    public List<ReservedRoom> get(Reservation domain){
        String query = properties.getProperty("get.res_room");
        List<ReservedRoom> reservedRooms = new ArrayList<>();
        return getExecutor().getReservedRoom(query, domain, result -> {
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
        });
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}