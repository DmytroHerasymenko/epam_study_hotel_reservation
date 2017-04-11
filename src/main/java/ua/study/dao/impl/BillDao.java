package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.domain.Bill;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class BillDao extends AbstractDao<Bill> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(BillDao.class);

    public BillDao(){
        init();
    }

    public boolean insert(Bill domain) {
        String insertTableSql = properties.getProperty("insert.bill") + domain.getReservationId()
                + ", " + domain.getTotalPrice() + ")";
        return getExecutor().executorUpdate(insertTableSql);
    }

    public Bill get(Long reservationId){
        String getBill = properties.getProperty("get.bill") + reservationId + "'";
        Bill bill = new Bill();

        getExecutor().executorQuery(getBill, result -> {
            if(!(result.next())) return null;
            bill.setBillId(result.getLong(1));
            bill.setReservationId(result.getLong(2));
            bill.setBillingDate(result.getDate(3).toLocalDate());
            bill.setTotalPrice(result.getInt(4));
            return bill;
        });
        return bill;
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
        String create = properties.getProperty("create.bill");
        getExecutor().executorUpdate(create);
    }
}