package ua.study.epam.dao.postgres;

import ua.study.epam.dao.GenericDao;
import ua.study.epam.dao.postgres.executor.Executor;
import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;

/**
 * Created by dima on 30.03.17.
 */
public abstract class AbstractDaoPostgres<T> implements GenericDao<T> {

    private final Executor executor = new Executor();

    private final ConnectionProxy connectionProxy;

    public AbstractDaoPostgres(ConnectionProxy connectionProxy){
        this.connectionProxy = connectionProxy;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public ConnectionProxy getConnectionProxy() {
        return connectionProxy;
    }
}
