package ua.study.dao.impl.transaction_helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by dima on 04.04.17.
 */
public class ConnectionProxy {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionProxy.class.getName());

    private final Connection connection;

    public ConnectionProxy(Connection connection){
        this.connection = connection;
    }

    public void begin(){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void savepoint(){
        try {
            connection.setSavepoint();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
