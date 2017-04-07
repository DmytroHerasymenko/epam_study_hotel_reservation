package ua.study.epam.dao.postgres;

import ua.study.epam.dao.postgres.executor.ResultHandler;
import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;
import ua.study.epam.domain.User;
import ua.study.epam.domain.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dima on 28.03.17.
 */
public class UserDaoPostgres extends AbstractDaoPostgres<User> {

    public UserDaoPostgres(ConnectionProxy connectionProxy) {
        super(connectionProxy);
    }

    public boolean insert(User domain) {
        String insert = "INSERT INTO users (name, login, password, user_role)" +
                " VALUES (?,?,?,?::role)";
        return getExecutor().userInsert(getConnectionProxy().getConnection(), domain, insert);
    }

    public User getClientByLogin(User domain){
        String selectSql = "SELECT * FROM users WHERE login = ?";

        ResultHandler resultHandler = new ResultHandlerImpl();

        return (User) getExecutor().getUser(getConnectionProxy().getConnection(), selectSql,
                domain.getLogin(), resultHandler);
    }

    public User verifyClientByLoginAndPassword(User domain){
        String selectSql = "SELECT * FROM users WHERE login = ? AND password = ?";

        ResultHandler resultHandler = new ResultHandlerImpl();

        return (User) getExecutor().verifyUser(getConnectionProxy().getConnection(), selectSql,
                domain.getLogin(), domain.getPassword(), resultHandler);
    }

    private class ResultHandlerImpl implements ResultHandler{

        public User handle(ResultSet result) throws SQLException {
            if(!(result.next())) return null;
            User client = new User();
            client.setUserId(result.getLong(1));
            client.setName(result.getString(2));
            client.setLogin(result.getString(3));
            client.setPassword(result.getString(4));
            client.setUserRole(UserRole.valueOf(result.getString(5)));
            return client;
        }
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