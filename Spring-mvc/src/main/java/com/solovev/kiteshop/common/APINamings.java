package com.solovev.kiteshop.common;

public class APINamings {
    public static final String MAIN = "http://localhost";
    public static final String CATALOG = "/catalog";
    public static final String CART = "/cart";
    public static final String ORDER = "/order";
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String LOGOUT = "/logout";

    public static String withMain(String endpoint) {
        return MAIN + endpoint;
    }
}
