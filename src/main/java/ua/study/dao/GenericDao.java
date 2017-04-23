package ua.study.dao;

import ua.study.dao.impl.executor.Executor;

/**
 * Created by dima on 30.03.17.
 */
public interface GenericDao<T> {
    void create();
    Executor getExecutor();
}
