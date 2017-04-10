package ua.study.dao.impl;

import ua.study.dao.AbstractDao;
import ua.study.domain.ReservedRoom;

import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class ReservedRoomDaoImpl extends AbstractDao<ReservedRoom> {

    public boolean insert(List<ReservedRoom> reservedRooms) {
        String insert = "INSERT INTO reserved_rooms (reservation_id, room_type_id) VALUES (?,?)";
        return getExecutor().reservedRoomInsert(reservedRooms, insert);
    }

    /*public boolean updateRoomNumber(ReservedRoom domain) {
        String insertTableSql =
                "INSERT INTO reserved_rooms (room_number) VALUES ("
                        + domain.getRoomNumber() + ")";
        return getExecutor().executorUpdate(insertTableSql);
    }*/
}