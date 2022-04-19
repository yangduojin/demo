package com.yx.demo.source.newspring;

import cn.hutool.core.lang.ClassScanner;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanDefinition;

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
    /**
     * 一级缓存 存放单例bean
     */
    private static Map<String,Object> singletonObjects = new HashMap();

    /**
     * 二级缓存 存放半成品bean,遇到接口会将接口实例化,从而导致报错
     */
    private final Map<String,Object> earlySingletonObjects = new HashMap();

    /**
     * 三级缓存,存放将接口转换成实例化的bean
     */
    private final Map<String,YxObjectFactory<?>> singletonFactories = new HashMap();

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

        if("singleton".equals(beanDefination.getScope())) {
            Object object = singletonObjects.get(beanName);
            if (object == null) {
                // 从二级缓存里面拿去半成品bean
                object = earlySingletonObjects.get(beanName);
                if(object == null){
                    // 如果是接口,先实例化三级缓存
                    if(beanDefination.getClazz().isInterface()){
                        addSingletonFactory(beanName, new YxMapperFactory(beanDefination.getClazz()));
                        YxObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                        object = singletonFactory.getObject();
                        this.earlySingletonObjects.put(beanName,object);
                        this.singletonFactories.remove(beanName);
                    }else {
                        this.earlySingletonObjects.put(beanName,beanDefination.getClazz().newInstance());
                    }
                    // 实例化
                    object = createBean(beanDefination.getClazz(),beanName,object);
                    singletonObjects.put(beanName, object);
                }
            }
            return object;
            //return object;
        } else {
            return createBean(beanDefination.getClazz(),beanName,null);
        }
    }

    private Object createBean(Class clazz,String beanname,Object tempObject) throws Exception {

        if (tempObject == null) {
            // 实例化
            tempObject = clazz.newInstance();
        }

        Object object = tempObject;
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

    protected void addSingletonFactory(String beanName, YxObjectFactory<?> singletonFactory){
        synchronized (this.earlySingletonObjects){
            if (!this.earlySingletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName,singletonFactory);
                this.earlySingletonObjects.remove(beanName);
            }
        }
    }

}
