package com.yx.demo.source.newspring;

import cn.hutool.core.lang.ClassScanner;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author yangxin
 * @Date 2022.4.18 10:18
 * @Version 1.0
 */
public class YxApplicationContext {
    private Class configClass;
    private Set<Class<?>> classes;
    private static Map<String, YxBeanDefination> beanDefinationMap = new HashMap();
    private static Map<String,Object> singletonObjects = new HashMap();
    private static final List<OneBeanPostProcessor> postProcessorList = new ArrayList<>();

    public YxApplicationContext(Class configClass) {

        this.configClass = configClass;
        // 解析配置类
        YxComponentScan annotation = (YxComponentScan) configClass.getAnnotation(YxComponentScan.class);
        String path = annotation.value();

        classes = ClassScanner.scanPackage(path);

        //classes.stream()
        //        .filter(c -> c.getAnnotation(YxComponent.class) != null )
        //        .forEach(c -> {
        //            YxBeanDefination beanDefination = new YxBeanDefination();
        //            beanDefination.setClazz(c);
        //            YxScope scope = (YxScope) configClass.getAnnotation(c);
        //            beanDefination.setScope(scope == null ? "singleton" : scope.value());
        //            YxComponent component = c.getAnnotation(YxComponent.class);
        //            beanDefinationMap.put(component.value(),beanDefination);
        //        });

         classes.stream()
                .filter(c -> c.getAnnotation(YxComponent.class) != null )
                .forEach(c -> {
                    loadBeanDefinition(c,configClass);

                    if(OneBeanPostProcessor.class.isAssignableFrom(c)){
                        try {
                            OneBeanPostProcessor beanPostProcessor = (OneBeanPostProcessor) c.newInstance();
                            postProcessorList.add(beanPostProcessor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public Object getBean(String beanName) throws Exception {
        YxBeanDefination beanDefination = beanDefinationMap.get(beanName);

        //Class clazz = classes.stream()
        //        .filter(c -> c.getAnnotation(Component.class) != null && c.getAnnotation(Component.class).value().equals(beanName))
        //        .findAny()
        //        .orElse(null);
        //System.out.println(clazz);
        //return null;

        if(beanDefination.getScope().equals("singleton")) {
            Object object = singletonObjects.get(beanName);
            if (object == null) {
                object = beanDefination.getClazz().newInstance();
                singletonObjects.put(beanName, object);
            }
            return createBean(beanDefination.getClazz(),beanName);
            //return object;
        } else {
            return createBean(beanDefination.getClazz(),beanName);
        }
    }

    private Object createBean(Class clazz,String beanname) throws Exception {
        Object object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields)
                .filter(f -> f.isAnnotationPresent(YxAutowired.class))
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        f.set(object,getBean(f.getName()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });

        postProcessorList.forEach(p -> p.postProcessBeforeInitialization(object,beanname));

        if(object instanceof YxInitializingBean){
            ((YxInitializingBean)object).afterPropertiesSet();
        }

        postProcessorList.forEach(p -> p.postProcessAfterInitialization(object,beanname));


        return object;
    }

    private void loadBeanDefinition(Class c, Class configClass) {
        YxBeanDefination beanDefinition = new YxBeanDefination();
        beanDefinition.setClazz(c);
        YxScope scope = (YxScope) configClass.getAnnotation(c);
        beanDefinition.setScope(scope == null ? "singleton" : scope.value());

        YxComponent component = (YxComponent) c.getAnnotation(YxComponent.class);
        beanDefinationMap.put(component.value(), beanDefinition);
    }

}
