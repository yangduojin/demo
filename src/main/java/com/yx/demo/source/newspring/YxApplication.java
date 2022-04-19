package com.yx.demo.source.newspring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yangxin
 * @Date 2022.4.18 10:13
 * @Version 1.0
 */
public class YxApplication {
    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext yxApplicationContext = new AnnotationConfigApplicationContext(YxConfig.class);
        //YxApplicationContext yxApplicationContext = new YxApplicationContext(YxConfig.class);
        UserService userService = (UserService) yxApplicationContext.getBean("userService");
        //System.out.println(userService);
        userService.test();

    }

}
