package ua.study.dao.impl;

import ua.study.dao.AbstractDao;
import ua.study.domain.Bill;

/**
 * Created by dima on 28.03.17.
 */
public class BillDaoImpl extends AbstractDao<Bill> {

    /*public boolean insert(Bill domain) {
        String insertTableSql = "INSERT INTO bills (reservation_id, total_price) VALUES ("
                + domain.getReservationId() + ", " + domain.getTotalPrice() + ")";

        return getExecutor().executorUpdate(insertTableSql);
    }*/
}