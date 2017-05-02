package ua.study.service;

import ua.study.service.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 30.03.17.
 */
public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private final Map<Class<? extends Service>, Service> services = new HashMap<>();

    private ServiceFactory(){
        init();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public <T> T getService(Class<T> classDto) {
        Service service = services.get(classDto);
        if(service == null) throw new IllegalArgumentException("service not found");
        return classDto.cast(service);
    }

    private void init(){
        services.put(BillService.class, new BillService());
        services.put(ReservationService.class, new ReservationService());
        services.put(ReservedRoomService.class, new ReservedRoomService());
        services.put(RoomTypeService.class, new RoomTypeService());
        services.put(UserService.class, new UserService());
    }
}