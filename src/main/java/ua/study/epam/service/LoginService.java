package ua.study.epam.service;

import ua.study.epam.domain.User;

/**
 * Created by dima on 31.03.17.
 */
public interface LoginService {
    User loginAndPasswordVerify(String login, String password);
}
