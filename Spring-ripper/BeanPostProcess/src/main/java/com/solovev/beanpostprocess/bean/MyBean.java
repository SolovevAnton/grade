package com.solovev.beanpostprocess.bean;

import com.solovev.beanpostprocess.annotaion.InjectRandomString;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MyBean {
    public static final String TO_INJECT = "One";

    @InjectRandomString
    private String emptyString;

    @InjectRandomString(strings = TO_INJECT)
    private String oneString;

    @InjectRandomString(strings = {"this", "orThat", "orOther"})
    private String someString;

    private String notAnnotated;
}
