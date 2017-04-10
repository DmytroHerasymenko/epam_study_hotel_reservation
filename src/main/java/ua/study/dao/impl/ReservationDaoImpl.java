package ua.study.dao.impl;

import ua.study.dao.AbstractDao;
import ua.study.domain.Reservation;
import ua.study.domain.ReservedRoom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public class ReservationDaoImpl extends AbstractDao<Reservation> {

    public Long insert(Reservation domain) {

        String insert = "INSERT INTO reservations " +
                "(client_id, arriving_date, departure_date, status) " +
                "VALUES (?,?,?,?::reservation_status) RETURNING reservation_id";

        return getExecutor().reservationInsert(domain, insert);
    }

    public boolean update(Reservation domain){

        String update = "UPDATE reservations SET status = '"
                + domain.getStatus() + "' WHERE reservation_id = '"
                + domain.getReservationId() + "'";

        return getExecutor().executorUpdate(update);
    }

    public List<ReservedRoom> checkDates(Reservation domain) {
        Timestamp arrive = Timestamp.valueOf(domain.getArrivingDate().atStartOfDay());
        Timestamp departure = Timestamp.valueOf(domain.getDepartureDate().atStartOfDay());

        String checkDates = "SELECT * FROM reserved_rooms rr JOIN reservations r ON " +
                "r.reservation_id = rr.reservation_id WHERE " +
                "(arriving_date <= '" + arrive + "' AND departure_date > '" + arrive + "')" +
                " AND status = 'CONFIRMED'" +
                " OR (arriving_date < '" + departure + "' AND departure_date >= '" + departure + "')" +
                " AND status = 'CONFIRMED'" +
                " OR ('" + arrive + "' <= arriving_date AND '" + departure + "' > arriving_date)" +
                " AND status = 'CONFIRMED'";

        List<ReservedRoom> reservedRooms = new ArrayList<>();
        getExecutor().executorQuery(checkDates, result -> {
            if(!(result.next())) return null;
            do {
                ReservedRoom reservedRoom = new ReservedRoom();
                reservedRoom.setReservedRoomId(result.getLong(1));
                reservedRoom.setReservationId(result.getLong(2));
                reservedRoom.setRoomTypeId(result.getLong(3));
                reservedRoom.setRoomNumber(result.getInt(4));
                reservedRooms.add(reservedRoom);
            } while(result.next());
            return reservedRooms;
        });
        return reservedRooms;
    }
}
