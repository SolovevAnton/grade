package com.solovev.dao.criteria;

import com.solovev.dao.UserDao;
import com.solovev.model.User;

import java.util.Map;
import java.util.Optional;

public class UserDaoCriteria extends CriteriaAbstractDAO<User> implements UserDao {
    public UserDaoCriteria() {
        super(User.class);
    }

    public Optional<User> getUserByLoginAndPass(String login, String password) {
        Map<String, Object> parametersAndValues = Map.of("login", login, "password", password);
        return getObjectByParam(parametersAndValues);
    }

    public Optional<User> getUserByHashAndId(String id, String hash) {
        Map<String, Object> parametersAndValues = Map.of("id", id, "cookieHash", hash);
        return getObjectByParam(parametersAndValues);
    }
}
