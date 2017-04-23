package ua.study.dao.impl.executor;

import ua.study.dao.impl.connection.ConnectionProxy;
import ua.study.dao.impl.connection.DataSource;

/**
 * Created by dima on 04.04.17.
 */
public class TransactionHelper {
    private static final TransactionHelper instance = new TransactionHelper();
    private final ThreadLocal<ConnectionProxy> threadLocal = new ThreadLocal<>();

    private TransactionHelper(){}

    public static TransactionHelper getInstance(){
        return instance;
    }

    ConnectionProxy getConnection(){
        ConnectionProxy connectionProxy = threadLocal.get();
        if(connectionProxy == null){
            connectionProxy = new ConnectionProxy(DataSource.getInstance().getConnection());
        }
        return connectionProxy;
    }

    public void beginTransaction(){
        if(isTransactionActive()){
            throw new IllegalStateException("transaction has already started");
        }
        ConnectionProxy connectionProxy = new ConnectionProxy(DataSource.getInstance().getConnection());
        connectionProxy.begin();
        threadLocal.set(connectionProxy);
    }

    public void rollbackTransaction(){
        if(!isTransactionActive()){
            throw new IllegalStateException("connection closed");
        }
        ConnectionProxy connectionProxy = threadLocal.get();
        connectionProxy.rollback();
        connectionProxy.close();
        threadLocal.remove();
    }

    public void commitTransaction(){
        if(!isTransactionActive()){
            throw new IllegalStateException("connection closed");
        }
        ConnectionProxy connectionProxy = threadLocal.get();
        connectionProxy.commit();
        connectionProxy.close();
        threadLocal.remove();
    }

    boolean isTransactionActive(){
        return threadLocal.get() != null;
    }
}
