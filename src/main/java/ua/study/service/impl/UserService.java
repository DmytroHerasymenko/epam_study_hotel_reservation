package ua.study.service.impl;

import ua.study.dao.DaoFactory;
import ua.study.dao.impl.UserDao;
import ua.study.domain.User;
import ua.study.service.Service;

/**
 * Created by dima on 18.04.17.
 */
public class UserService implements Service {

    public boolean registration(String name, String login, String password) {
        UserDao userDao = DaoFactory.getInstance().getDao("UserDao", UserDao.class);
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        return userDao.insert(user);
    }

    public User loginAndPasswordVerify(String login, String password){
        DaoFactory factory = DaoFactory.getInstance();
        UserDao userDao = factory.getDao("UserDao", UserDao.class);
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userDao.verifyUserByLoginAndPassword(user);
    }

    public User getUserByLogin(String login){
        UserDao userDao = DaoFactory.getInstance().getDao("UserDao", UserDao.class);
        User user = new User();
        user.setLogin(login);
        return userDao.get(user);
    }
}