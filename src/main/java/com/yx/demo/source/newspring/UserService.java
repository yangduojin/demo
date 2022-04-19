package com.yx.demo.source.newspring;

import com.yx.demo.ChargeStatus;
import com.yx.demo.source.newspring.mapper.UserMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.metrics.StartupStep;

/**
 * @Author yangxin
 * @Date 2022.4.18 10:14
 * @Version 1.0
 */
@YxComponent(value = "userService")
public class UserService implements YxInitializingBean {

    @YxAutowired
    private TestService testService;

    //// 因为mysql不知道怎么处理 所以暂时取消userMapper
    //@YxAutowired
    //private UserMapper userMapper;

    private String password;

    public void test() {
        //// userMapper不能使用,所以注释
        // userMapper.selectList();
        System.out.println(testService.test());
    }

    @Override
    public void afterPropertiesSet() {
        password = "从远处获得密码";
        System.out.println(password);
    }

    public static void test1(ChargeStatus chargeStatus){
        System.out.println(chargeStatus == ChargeStatus.PROCESS);
    }

    public static void main(String[] args) {
        test1(ChargeStatus.PROCESS);
    }

}
