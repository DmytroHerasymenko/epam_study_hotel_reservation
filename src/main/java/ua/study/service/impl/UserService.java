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
        UserDao userDao = DaoFactory.getInstance().getDao(UserDao.class);
        User user = setLoginAndPassword(login, password);
        user.setName(name);
        return userDao.insert(user);
    }

    public User loginAndPasswordVerify(String login, String password){
        UserDao userDao = DaoFactory.getInstance().getDao(UserDao.class);
        User user = setLoginAndPassword(login, password);
        return userDao.verifyUserByLoginAndPassword(user);
    }

    private User setLoginAndPassword(String login, String password){
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}