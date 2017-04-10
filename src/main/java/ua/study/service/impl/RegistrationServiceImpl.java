package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.dao.impl.UserDaoImpl;
import ua.study.domain.User;
import ua.study.service.RegistrationService;

/**
 * Created by dima on 30.03.17.
 */
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public boolean registration(String name, String login, String password) {
        DaoFactory factory = DaoFactory.getInstance();
        UserDaoImpl clientDao = factory.getDao("UserDao", UserDaoImpl.class);
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        //user.setUserRole(UserRole.USER);
        TransactionHelper.getInstance().beginTransaction();
        boolean isSuccess = clientDao.insert(user);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return isSuccess;
    }
}