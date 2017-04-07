package ua.study.epam.dao.postgres;

import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;
import ua.study.epam.domain.Reservation;

/**
 * Created by dima on 28.03.17.
 */
public class ReservationDaoPostgres extends AbstractDaoPostgres<Reservation> {

    public ReservationDaoPostgres(ConnectionProxy connectionProxy) {
        super(connectionProxy);
    }

    public Long insert(Reservation domain) {

        String insert = "INSERT INTO reservations " +
                "(client_id, arriving_date, departure_date, status) " +
                "VALUES (?,?,?,?::reservation_status) RETURNING reservation_id";

        return getExecutor().reservationInsert(getConnectionProxy().getConnection(), domain, insert);
    }
}
