package ua.study.epam.service;

import ua.study.epam.dao.postgres.transaction_helper.TransactionHelper;
import ua.study.epam.service.impl.LoginServiceImpl;
import ua.study.epam.service.impl.RegistrationServiceImpl;
import ua.study.epam.service.impl.ReservationServiceImpl;

/**
 * Created by dima on 30.03.17.
 */
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    private final TransactionHelper transactionHelper = new TransactionHelper();

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public RegistrationService getRegistrationService(){
        return new RegistrationServiceImpl(transactionHelper);
    }

    public LoginService getLoginService() {
        return new LoginServiceImpl(transactionHelper);
    }

    public ReservationService getReservationService() {
        return new ReservationServiceImpl(transactionHelper);
    }
}
