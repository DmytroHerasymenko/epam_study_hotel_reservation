package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.dao.impl.executor.ResultHandler;
import ua.study.domain.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dima on 28.03.17.
 */
public class UserDao extends AbstractDao<User> {
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(UserDao.class.getName());

    public UserDao(){
        init();
    }

    @Override
    public void create() {
        getExecutor().executorUpdate(properties.getProperty("create.user"));
    }

    public boolean insert(User domain) {
        return getExecutor().insertUser(domain, properties.getProperty("insert.user"));
    }

    public User get(User domain){
        return (User) getExecutor().getByLogin(properties.getProperty("get.user"), domain, new ResultHandlerUser());
    }

    public User verifyUserByLoginAndPassword(User domain){
        return (User) getExecutor().verifyUser(properties.getProperty("verify.user"), domain, new ResultHandlerUser());
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/sql.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private class ResultHandlerUser implements ResultHandler {
        public User handle(ResultSet result) throws SQLException {
            if(!(result.next())) return null;
            User client = new User();
            client.setUserId(result.getLong(1));
            client.setName(result.getString(2));
            client.setLogin(result.getString(3));
            client.setPassword(result.getString(4));
            return client;
        }
    }
}