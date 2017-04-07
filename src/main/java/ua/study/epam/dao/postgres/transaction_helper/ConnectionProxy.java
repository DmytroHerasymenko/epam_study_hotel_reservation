package ua.study.epam.dao.postgres.transaction_helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGConnectionPoolDataSource;
import ua.study.epam.dao.DataSourceFactory;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by dima on 04.04.17.
 */
public class ConnectionProxy {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionProxy.class);

    private final PGConnectionPoolDataSource dataSource = DataSourceFactory.getDataSource();
    //private PooledConnection pooledConnection;
    private Connection connection;
    private boolean isTransactionStart = false;

    public void begin(){
        try {
            //pooledConnection.getConnection().setAutoCommit(false);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void commit(){
        try {
            //pooledConnection.getConnection().commit();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void savepoint(){
        try {
            //pooledConnection.getConnection().setSavepoint();
            connection.setSavepoint();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void rollback(){
        try {
            //pooledConnection.getConnection().rollback();
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void close(){
        try {
            //pooledConnection.close();
            connection.close();
            isTransactionStart = false;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Connection getConnection() {
        //Connection connection = null;
        if(!isTransactionStart){
            try {
                //pooledConnection = dataSource.getPooledConnection();
                connection = dataSource.getPooledConnection().getConnection();
                isTransactionStart = true;
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                isTransactionStart = false;
            }
        }
        return connection;
    }
}
