package ua.study.service.impl;

import ua.study.service.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class ServiceFactoryImpl implements ServiceFactory {

    private static final ServiceFactoryImpl instance = new ServiceFactoryImpl();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private final Map<String, Service> services = new HashMap<>();

    private ServiceFactoryImpl(){
        init();
    }

    @Override
    public <T> T getService(String name, Class<T> classDto) {
        Service service = services.get(name);
        if(service == null) throw new IllegalArgumentException("service not found");
        return classDto.cast(service);
    }

    private void init(){
        services.put("BillService", new BillService());
        services.put("ReservationService", new ReservationService());
        services.put("ReservedRoomService", new ReservedRoomService());
        services.put("RoomService", new RoomService());
        services.put("RoomTypeService", new RoomTypeService());
        services.put("UserService", new UserService());
    }
}