package ua.study.dao;

import ua.study.dao.impl.transaction_helper.Executor;

/**
 * Created by dima on 30.03.17.
 */
public abstract class AbstractDao<T> implements GenericDao<T> {

    private final Executor executor = new Executor();

    @Override
    public Executor getExecutor() {
        return executor;
    }
}
