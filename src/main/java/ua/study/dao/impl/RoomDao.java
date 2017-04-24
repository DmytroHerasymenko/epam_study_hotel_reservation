package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;

import java.io.IOException;
import java.util.*;

/**
 * Created by dima on 28.03.17.
 */
public class RoomDao extends AbstractDao {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(RoomDao.class.getName());

    public RoomDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.room"));
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
