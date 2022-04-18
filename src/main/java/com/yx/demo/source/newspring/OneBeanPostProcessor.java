package com.yx.demo.source.newspring;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;


public interface OneBeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}