package ua.study.epam.dao.postgres;

import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;
import ua.study.epam.domain.Bill;

/**
 * Created by dima on 28.03.17.
 */
public class BillDaoPostgres extends AbstractDaoPostgres<Bill> {

    public BillDaoPostgres(ConnectionProxy connectionProxy){
        super(connectionProxy);
    }

    /*public boolean insert(Bill domain) {
        String insertTableSql = "INSERT INTO bills (reservation_id, total_price) VALUES ("
                + domain.getReservationId() + ", " + domain.getTotalPrice() + ")";

        return getExecutor().executorUpdate(getConnectionProxy().getConnection(), insertTableSql);
    }*/
}