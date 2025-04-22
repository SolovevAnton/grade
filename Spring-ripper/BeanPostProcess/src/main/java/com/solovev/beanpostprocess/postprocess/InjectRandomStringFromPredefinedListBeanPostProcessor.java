package com.solovev.beanpostprocess.postprocess;

import com.solovev.beanpostprocess.annotaion.InjectRandomString;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

@Component
@Slf4j
public class InjectRandomStringFromPredefinedListBeanPostProcessor implements BeanPostProcessor {
    private final Random random = new Random();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("postProcessBeforeInitialization::beanName = {}, beanClass = {}", beanName,
                bean.getClass().getSimpleName());
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(InjectRandomString.class))
                .forEach(f -> injectString(f, bean));
        return bean;
    }

    @SneakyThrows
    private void injectString(Field field, Object bean) {
        field.setAccessible(true);
        InjectRandomString annotation = field.getAnnotation(InjectRandomString.class);
        field.set(bean, getStringToInject(annotation));
    }

    private String getStringToInject(InjectRandomString annotation) {
        String[] pool = annotation.strings();
        return pool.length == 0
                ? ""
                : pool[random.nextInt(pool.length)];
    }
}
