package com.yx.demo.source.newspring;

/**
 * @Author yangxin
 * @Date 2022.4.18 10:14
 * @Version 1.0
 */
@YxComponent(value = "testService")
public class TestService {

    @YxAutowired
    private UserService userService;

    public String test() {
        return "我是TestService";
    }
}
