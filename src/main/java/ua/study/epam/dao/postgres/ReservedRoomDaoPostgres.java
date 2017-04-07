package ua.study.epam.dao.postgres;

import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;
import ua.study.epam.domain.ReservedRoom;

import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class ReservedRoomDaoPostgres extends AbstractDaoPostgres<ReservedRoom> {

    public ReservedRoomDaoPostgres(ConnectionProxy connectionProxy) {
        super(connectionProxy);
    }

    public boolean insert(List<ReservedRoom> reservedRooms) {
        String insert = "INSERT INTO reserved_rooms (reservation_id, room_type_id) VALUES (?,?)";
        return getExecutor().reservedRoomInsert(getConnectionProxy().getConnection(), reservedRooms, insert);
    }

    /*public boolean updateRoomNumber(ReservedRoom domain) {
        String insertTableSql =
                "INSERT INTO reserved_rooms (room_number) VALUES ("
                        + domain.getRoomNumber() + ")";
        return getExecutor().executorUpdate(getConnectionProxy().getConnection(), insertTableSql);
    }*/
}