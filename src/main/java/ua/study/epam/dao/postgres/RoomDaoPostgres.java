package ua.study.epam.dao.postgres;

import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;
import ua.study.epam.domain.Room;

/**
 * Created by dima on 28.03.17.
 */
public class RoomDaoPostgres extends AbstractDaoPostgres<Room> {

    public RoomDaoPostgres(ConnectionProxy connectionProxy) {
        super(connectionProxy);
    }
}
