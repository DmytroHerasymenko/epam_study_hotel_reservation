package ua.study.dao;

import ua.study.dao.impl.transaction_helper.Executor;

/**
 * Created by dima on 30.03.17.
 */
public interface GenericDao<T> {

    Executor getExecutor();
}
