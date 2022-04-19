package com.yx.demo.source.newspring;

import org.springframework.beans.BeansException;

/**
 * @Author yangxin
 * @Date 2022.4.19 11:46
 * @Version 1.0
 */
@FunctionalInterface
public interface YxObjectFactory<T> {
    T getObject() throws BeansException;
}
