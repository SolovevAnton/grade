package com.solovev.springrestsoap.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class APIEndpoints {
    public static final String USERS = "users";
    public static final String USERS_REST = "rest/" + USERS;
    public static final String SOAP = "ws/";
    public static final String USERS_SOAP = SOAP + USERS;
}
