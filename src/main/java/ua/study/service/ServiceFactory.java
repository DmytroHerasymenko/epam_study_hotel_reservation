package ua.study.service;

import ua.study.service.impl.ServiceFactoryImpl;

/**
 * Created by dima on 18.04.17.
 */
public interface ServiceFactory {

    static ServiceFactory getInstance(){
        return ServiceFactoryImpl.getInstance();
    }

    <T> T getService(String name, Class<T> classDto);
}
