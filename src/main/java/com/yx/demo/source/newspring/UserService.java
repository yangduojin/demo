package com.yx.demo.source.newspring;

/**
 * @Author yangxin
 * @Date 2022.4.18 10:14
 * @Version 1.0
 */
@YxComponent(value = "userService")
public class UserService implements YxInitializingBean {

    @YxAutowired
    private TestService testService;

    private String password;

    public void test() {
        System.out.println(testService.test());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        password = "从远处获得密码";
        System.out.println(password);
    }
}
