package ua.study.epam.service.impl;

import ua.study.epam.dao.DaoFactory;
import ua.study.epam.dao.postgres.UserDaoPostgres;
import ua.study.epam.dao.postgres.transaction_helper.TransactionHelper;
import ua.study.epam.domain.User;
import ua.study.epam.domain.enums.UserRole;
import ua.study.epam.service.RegistrationService;

/**
 * Created by dima on 30.03.17.
 */
public class RegistrationServiceImpl implements RegistrationService {

    private final TransactionHelper transactionHelper;

    public RegistrationServiceImpl(TransactionHelper transactionHelper){
        this.transactionHelper = transactionHelper;
    }

    @Override
    public boolean registration(String name, String login, String password) {
        DaoFactory factory = DaoFactory.getInstance(transactionHelper.getTransaction(), DaoFactory.DaoFactoryType.POSTGRES);
        UserDaoPostgres clientDao = factory.getDao("UserDao", UserDaoPostgres.class);
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        user.setUserRole(UserRole.USER);
        boolean isSuccess = clientDao.insert(user);
        transactionHelper.closeTransaction();
        return isSuccess;
    }
}
