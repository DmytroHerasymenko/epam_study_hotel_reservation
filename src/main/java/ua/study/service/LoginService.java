package ua.study.service;

import ua.study.domain.User;

/**
 * Created by dima on 31.03.17.
 */
public interface LoginService {
    User loginAndPasswordVerify(String login, String password);
}
