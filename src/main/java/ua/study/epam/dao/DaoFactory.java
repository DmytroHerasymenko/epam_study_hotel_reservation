package ua.study.epam.dao;

import ua.study.epam.dao.postgres.DaoFactoryPostgres;
import ua.study.epam.dao.postgres.transaction_helper.ConnectionProxy;

/**
 * Created by dima on 28.03.17.
 */
public interface DaoFactory {

    enum DaoFactoryType {
        POSTGRES;
    }

    <T> T getDao(String name, Class<T> classDto);

    static DaoFactory getInstance(ConnectionProxy connectionProxy, DaoFactoryType daoFactoryType){
        switch (daoFactoryType){
            case POSTGRES: return DaoFactoryPostgres.getInstance(connectionProxy);
            default: throw new IllegalArgumentException("dao factory not found");
        }
    }
}