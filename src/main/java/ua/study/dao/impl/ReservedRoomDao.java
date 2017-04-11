package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.dao.impl.transaction_helper.ResultHandler;
import ua.study.domain.ReservedRoom;

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
    private final Logger LOGGER = LogManager.getLogger(BillDao.class);

    public ReservedRoomDao(){
        init();
    }

    public boolean insert(List<ReservedRoom> reservedRooms) {
        String insert = properties.getProperty("insert.res_room");
        return getExecutor().reservedRoomInsert(reservedRooms, insert);
    }

    public boolean update(List<ReservedRoom> reservedRooms) {
        String update = properties.getProperty("update.res_room");
        return getExecutor().reservedRoomUpdate(reservedRooms, update);
    }

    public List<ReservedRoom> get(Long reservationId){
        String query = properties.getProperty("get.res_room") + reservationId + "'";
        List<ReservedRoom> reservedRooms = new ArrayList<>();
        return getExecutor().executorQuery(query, result -> {
            if(!(result.next())) return null;
            do {
                ReservedRoom reservedRoom = new ReservedRoom();
                reservedRoom.setReservedRoomId(result.getLong(1));
                reservedRoom.setReservationId(result.getLong(2));
                reservedRoom.setRoomTypeId(result.getLong(3));
                reservedRoom.setRoomNumber(result.getInt(4));
                reservedRooms.add(reservedRoom);
            } while (result.next());
            return reservedRooms;
        });
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
        String createTableSql = properties.getProperty("create.res_room");
        getExecutor().executorUpdate(createTableSql);
    }
}