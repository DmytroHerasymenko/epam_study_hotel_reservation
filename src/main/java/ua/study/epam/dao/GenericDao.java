package ua.study.epam.dao;

import ua.study.epam.dao.postgres.executor.Executor;
import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;

/**
 * Created by dima on 30.03.17.
 */
public interface GenericDao<T> {

    Executor getExecutor();
    ConnectionProxy getConnectionProxy();
}
