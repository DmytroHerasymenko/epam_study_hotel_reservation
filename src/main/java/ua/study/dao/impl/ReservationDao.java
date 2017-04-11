package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class ReservationDao extends AbstractDao<Reservation> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(BillDao.class);

    public ReservationDao(){
        init();
    }

    public Long insert(Reservation domain) {
        String insert = properties.getProperty("insert.reservation");
        return getExecutor().reservationInsert(domain, insert);
    }

    public boolean update(Reservation domain){
        String update = properties.getProperty("update1.reservation") + domain.getStatus()
                + properties.getProperty("update2.reservation") + domain.getReservationId() + "'";
        return getExecutor().executorUpdate(update);
    }

    public List<ReservedRoom> checkDates(Reservation domain) {
        Timestamp arrive = Timestamp.valueOf(domain.getArrivingDate().atStartOfDay());
        Timestamp departure = Timestamp.valueOf(domain.getDepartureDate().atStartOfDay());
        List<ReservedRoom> reservedRooms = new ArrayList<>();
        String checkDates = properties.getProperty("check1.reservation") + arrive +
                properties.getProperty("check2.reservation") + arrive +
                properties.getProperty("check3.reservation") + departure +
                properties.getProperty("check4.reservation") + departure +
                properties.getProperty("check5.reservation") + arrive +
                properties.getProperty("check6.reservation") + departure +
                properties.getProperty("check7.reservation");
        return getExecutor().executorQuery(checkDates, result -> {
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
        } );
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
        String create = properties.getProperty("create.reservation");
        String createEnum = properties.getProperty("enum.reservation");
        getExecutor().executorUpdate(createEnum);
        getExecutor().executorUpdate(create);
    }
}
