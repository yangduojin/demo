package com.yx.demo.source.newspring.mybatis;

import java.lang.reflect.Proxy;

public class YxSession {
   public static Object dealSql(Class clazz) {
       Class c[] = new Class[]{clazz};
       return Proxy.newProxyInstance(YxSession.class.getClassLoader(), c, new YxInvocationHandler("testBiz"));
   }
}
