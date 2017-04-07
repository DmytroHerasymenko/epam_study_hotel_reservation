package ua.study.epam.service.impl;

import ua.study.epam.dao.DaoFactory;
import ua.study.epam.dao.postgres.UserDaoPostgres;
import ua.study.epam.dao.postgres.transaction_helper.TransactionHelper;
import ua.study.epam.domain.User;
import ua.study.epam.service.LoginService;

/**
 * Created by dima on 31.03.17.
 */
public class LoginServiceImpl implements LoginService {

    private final TransactionHelper transactionHelper;

    public LoginServiceImpl(TransactionHelper transactionHelper){
        this.transactionHelper = transactionHelper;
    }

    @Override
    public User loginAndPasswordVerify(String login, String password){
        DaoFactory factory = DaoFactory.getInstance(transactionHelper.getTransaction(), DaoFactory.DaoFactoryType.POSTGRES);
        UserDaoPostgres clientDao = factory.getDao("UserDao", UserDaoPostgres.class);
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        User client = clientDao.verifyClientByLoginAndPassword(user);
        transactionHelper.closeTransaction();
        return client;
    }
}