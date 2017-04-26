package ua.study.dao.impl.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by dima on 04.04.17.
 */
public class ConnectionProxy {
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

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("some problem with database connection ", e);
        }
    }
}
