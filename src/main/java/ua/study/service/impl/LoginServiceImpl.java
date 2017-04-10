package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.transaction_helper.TransactionHelper;
import ua.study.dao.impl.UserDaoImpl;
import ua.study.domain.User;
import ua.study.service.LoginService;

/**
 * Created by dima on 31.03.17.
 */
public class LoginServiceImpl implements LoginService {

    @Override
    public User loginAndPasswordVerify(String login, String password){
        DaoFactory factory = DaoFactory.getInstance();
        UserDaoImpl clientDao = factory.getDao("UserDao", UserDaoImpl.class);
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        TransactionHelper.getInstance().beginTransaction();
        User client = clientDao.verifyClientByLoginAndPassword(user);
        TransactionHelper.getInstance().commitTransaction();
        TransactionHelper.getInstance().closeTransaction();
        return client;
    }
}