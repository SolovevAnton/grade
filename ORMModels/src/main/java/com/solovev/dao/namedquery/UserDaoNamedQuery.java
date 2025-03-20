package com.solovev.dao.namedquery;

import com.solovev.dao.UserDao;
import com.solovev.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDaoNamedQuery implements UserDao {

    private final DaoFacade<User> daoFacade = new DaoFacade<>(User.class);

    @Override
    public Optional<User> getUserByLoginAndPass(String login, String password) {
        Map<String, Object> parametersAndValues = Map.of("login", login, "password", password);
        return daoFacade.getResult("User_getByLogAndPass", parametersAndValues);
    }

    @Override
    public Optional<User> getUserByHashAndId(String id, String hash) {
        Map<String, Object> parametersAndValues = Map.of("id", Long.parseLong(id), "cookieHash", hash);
        return daoFacade.getResult("User_getByHashAndId", parametersAndValues);
    }

    @Override
    public Optional<User> get(long id) {
        return daoFacade.getResult("User_getById", "id", id);
    }

    @Override
    public Collection<User> get() {
        return daoFacade.getResults("User_getAll");
    }

    @Override
    public boolean add(User elem) throws IllegalArgumentException {
        Map<String, Object> userMap = getUserMap(elem);
        var id = daoFacade.executeNativeUpdate("User_insert", userMap, elem);
        elem.setId(id);
        return true;
    }

    private Map<String, Object> getUserMap(User elem) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("login", elem.getLogin());
        userMap.put("password", elem.getPassword());
        userMap.put("name", elem.getName());
        userMap.put("registrationDate", elem.getRegistrationDate());
        userMap.put("cookieHash", elem.getCookieHash());
        return userMap;
    }

    @Override
    public Optional<User> delete(long id) {
        var found = get(id);
        found.ifPresent(__ -> daoFacade.executeNamedUpdate("User_delete", Map.of("id", id)));
        return found;
    }

    @Override
    public boolean update(User elem) {
        var userMap = getUserMap(elem);
        userMap.put("id", elem.getId());
        return daoFacade.executeNamedUpdate("User_update", userMap);
    }
}
