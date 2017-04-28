package ua.study.service;

import ua.study.service.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private final Map<String, Service> services = new HashMap<>();

    private ServiceFactory(){
        init();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public <T> T getService(String name, Class<T> classDto) {
        Service service = services.get(name);
        if(service == null) throw new IllegalArgumentException("service not found");
        return classDto.cast(service);
    }

    private void init(){
        services.put("BillService", new BillService());
        services.put("ReservationService", new ReservationService());
        services.put("ReservedRoomService", new ReservedRoomService());
        services.put("RoomTypeService", new RoomTypeService());
        services.put("UserService", new UserService());
    }
}