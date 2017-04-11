package ua.study.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.AbstractDao;
import ua.study.dao.impl.transaction_helper.ResultHandler;
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
    private final Logger LOGGER = LogManager.getLogger(BillDao.class);

    public UserDao(){
        init();
    }

    public boolean insert(User domain) {
        String insert = properties.getProperty("insert.user");
        return getExecutor().userInsert(domain, insert);
    }

    public User get(User domain){
        String get = properties.getProperty("get_by_login.user");
        return (User) getExecutor().getUser(get, domain.getLogin(), new ResultHandlerUser());
    }

    public User verifyClientByLoginAndPassword(User domain){
        String selectSql = properties.getProperty("verify.user");
        return (User) getExecutor().verifyUser(selectSql, domain, new ResultHandlerUser());
    }

    private void init(){
        try {
            properties.load(getClass().getResourceAsStream("/dao.properties"));
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
    @Override
    public void create() {
        String create = properties.getProperty("create.user");
        getExecutor().executorUpdate(create);
    }
}