package com.solovev.beanconfiguration.bean;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class MethodReplacerImpl implements MethodReplacer {
    public static final String REPLACEMENT_STRING = "replaced method string";

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        return REPLACEMENT_STRING;
    }
}
