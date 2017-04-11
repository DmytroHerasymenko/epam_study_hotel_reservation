package ua.study.service;

import ua.study.service.impl.*;

/**
 * Created by dima on 30.03.17.
 */
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final LoginService loginService = new LoginServiceImpl();
    private final ReservationService reservationService = new ReservationServiceImpl();
    private final BillService billService = new BillServiceImpl();
    private final ReservedRoomService reservedRoomService = new ReservedRoomServiceImpl();

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public RegistrationService getRegistrationService(){
        return registrationService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public BillService getBillService(){
        return billService;
    }

    public ReservedRoomService getReservedRoomService(){
        return reservedRoomService;
    }
}
