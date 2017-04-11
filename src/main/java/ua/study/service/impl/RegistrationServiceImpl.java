package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.dao.impl.UserDao;
import ua.study.domain.User;
import ua.study.service.RegistrationService;

/**
 * Created by dima on 30.03.17.
 */
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public boolean registration(String name, String login, String password) {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao clientDao = factory.getDao("UserDao", UserDao.class);
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        TransactionHelper.getInstance().beginTransaction();
        boolean isSuccess = clientDao.insert(user);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return isSuccess;
    }
}
