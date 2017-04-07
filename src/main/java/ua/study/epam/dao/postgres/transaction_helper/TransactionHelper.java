package ua.study.epam.dao.postgres.transaction_helper;

/**
 * Created by dima on 04.04.17.
 */
public class TransactionHelper {

    private final ThreadLocal<ConnectionProxy> connectionThreadLocal = new ThreadLocal<>();

    public ConnectionProxy getTransaction(){
        if(connectionThreadLocal.get() == null){
            connectionThreadLocal.set(new ConnectionProxy());
        }
        return connectionThreadLocal.get();
    }

    public void beginTransaction(){
        getTransaction().begin();
    }

    public void rollbackTransaction(){
        getTransaction().rollback();
    }

    public void commitTransaction(){
        getTransaction().commit();
    }

    public void closeTransaction(){
        getTransaction().close();
        //connectionThreadLocal.remove();
    }
}
