package ua.study.dao;

import ua.study.dao.impl.connection.Executor;

/**
 * Created by dima on 30.03.17.
 */
public interface GenericDao<T> {
    void create();
    Executor getExecutor();
}
