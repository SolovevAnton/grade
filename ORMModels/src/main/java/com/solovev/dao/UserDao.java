package com.solovev.dao;

import com.solovev.model.User;

import java.util.Optional;

public interface UserDao extends DAO<User> {

    Optional<User> getUserByLoginAndPass(String login, String password);

    Optional<User> getUserByHashAndId(String id, String hash);
}
