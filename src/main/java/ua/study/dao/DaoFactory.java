package ua.study.dao;

import ua.study.dao.impl.DaoFactoryImpl;

/**
 * Created by dima on 28.03.17.
 */
public interface DaoFactory {

    static DaoFactory getInstance(){
        return DaoFactoryImpl.getInstance();
    }

    <T> T getDao(String name, Class<T> classDto);
}