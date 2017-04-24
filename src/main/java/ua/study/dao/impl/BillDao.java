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
    private final Logger LOGGER = LogManager.getLogger(BillDao.class.getName());

    public BillDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.bill"));
    }

    public boolean insert(Bill domain) {
        return getExecutor().insertBill(domain, properties.getProperty("insert.bill"));
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}