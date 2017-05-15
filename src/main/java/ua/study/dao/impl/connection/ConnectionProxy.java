package ua.study.dao.impl.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Created by dima on 04.04.17.
 */
public class ConnectionProxy implements AutoCloseable {
    private final Connection connection;
    private final Logger LOGGER = LogManager.getLogger(ConnectionProxy.class.getName());

    public ConnectionProxy(Connection connection){
        this.connection = connection;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public Statement createStatement() throws SQLException {
            return connection.createStatement();
    }

    public void begin(){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("some problem with database connection ", e);
        }
    }

    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("some problem with database connection ", e);
        }
    }

    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("some problem with database connection ", e);
        }
    }

    public Array createArrayOf(String type, Object[] array) throws SQLException {
        return connection.createArrayOf(type, array);
    }

    public void close() {
        if (!TransactionHelper.getInstance().isTransactionActive()) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("some problem with database connection ", e);
            }
        }
    }
}
