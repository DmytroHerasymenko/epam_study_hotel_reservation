package ua.study.dao.impl.transaction_helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.dao.DataSourceFactory;

import java.sql.SQLException;

/**
 * Created by dima on 04.04.17.
 */
public class TransactionHelper {

    private static final Logger LOGGER = LogManager.getLogger(TransactionHelper.class.getName());

    private final ThreadLocal<ConnectionProxy> connectionThreadLocal = new ThreadLocal<>();
    private static TransactionHelper instance = new TransactionHelper();

    private TransactionHelper(){}

    public static TransactionHelper getInstance(){
        return instance;
    }

    ConnectionProxy getConnection(){
        return connectionThreadLocal.get();
    }

    public void beginTransaction(){
        ConnectionProxy connectionProxy = connectionThreadLocal.get();
        if(connectionProxy == null){
            try {
                connectionProxy = new ConnectionProxy(DataSourceFactory
                        .getDataSource().getPooledConnection().getConnection());
                connectionThreadLocal.set(connectionProxy);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        connectionProxy.begin();
    }

    public void rollbackTransaction(){
        getConnection().rollback();
    }

    public void commitTransaction(){
        getConnection().commit();
    }

    public void closeTransaction(){
        getConnection().close();
        connectionThreadLocal.remove();
    }
}
