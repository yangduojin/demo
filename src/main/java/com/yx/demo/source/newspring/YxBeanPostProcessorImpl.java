package com.yx.demo.source.newspring;

import org.springframework.beans.BeansException;

/**
 * @Author yangxin
 * @Date 2022.4.18 17:35
 * @Version 1.0
 */
@YxComponent("yxBeanPostProcessorImpl")
public class YxBeanPostProcessorImpl implements OneBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("前置,我是初始化前置处理");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置,我是初始化后置处理");
        return bean;
    }
}
