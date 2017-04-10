package ua.study.dao.impl;

import ua.study.dao.AbstractDao;
import ua.study.dao.impl.transaction_helper.ResultHandler;
import ua.study.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dima on 28.03.17.
 */
public class UserDaoImpl extends AbstractDao<User> {

    private class ResultHandlerImpl implements ResultHandler {

        public User handle(ResultSet result) throws SQLException {
            if(!(result.next())) return null;
            User client = new User();
            client.setUserId(result.getLong(1));
            client.setName(result.getString(2));
            client.setLogin(result.getString(3));
            client.setPassword(result.getString(4));
            //client.setUserRole(UserRole.valueOf(result.getString(5)));
            return client;
        }
    }

    public boolean insert(User domain) {
        String insert = "INSERT INTO users (name, login, password)" +
                " VALUES (?,?,?)";
        return getExecutor().userInsert(domain, insert);
    }

    public User getClientByLogin(User domain){
        String selectSql = "SELECT * FROM users WHERE login = ?";

        return (User) getExecutor().getUser(selectSql, domain.getLogin(), new ResultHandlerImpl());
    }

    public User verifyClientByLoginAndPassword(User domain){
        String selectSql = "SELECT * FROM users WHERE login = ? AND password = ?";

        return (User) getExecutor().verifyUser(selectSql, domain.getLogin(),
                domain.getPassword(), new ResultHandlerImpl());
    }
}

/*public User getClientByLogin(User domain){
        String selectSql = "SELECT * FROM users WHERE login = ?";

        ResultHandler resultHandler = new ResultHandlerImpl();

        return getExecutor().getUser(getConnectionProxy().getConnection(), selectSql,
                domain.getLogin(), result -> {
                    if(!(result.next())) return null;
                    User client = new User();
                    client.setUserId(result.getLong(1));
                    client.setName(result.getString(2));
                    client.setLogin(result.getString(3));
                    client.setPassword(result.getString(4));
                    client.setUserRole(UserRole.valueOf(result.getString(5)));
                    return client;
                });
    }*/

/*public User verifyClientByLoginAndPassword(User domain){
        String selectSql = "SELECT * FROM users WHERE login = ? AND password = ?";

        return getExecutor().verifyUser(getConnectionProxy().getConnection(), selectSql,
                domain.getLogin(), domain.getPassword(), result -> {
                    if(!(result.next())) return null;
                    User client = new User();
                    client.setUserId(result.getLong(1));
                    client.setName(result.getString(2));
                    client.setLogin(result.getString(3));
                    client.setPassword(result.getString(4));
                    client.setUserRole(UserRole.valueOf(result.getString(5)));
                    return client;
                });
    }*/