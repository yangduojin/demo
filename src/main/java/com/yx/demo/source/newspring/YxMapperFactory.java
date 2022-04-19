package com.yx.demo.source.newspring;

import com.yx.demo.source.newspring.mybatis.YxSession;
import org.springframework.beans.BeansException;

/**
 * @Author yangxin
 * @Date 2022.4.19 11:48
 * @Version 1.0
 */
@YxComponent("yxMapperFactory")
public class YxMapperFactory implements YxObjectFactory {

    private Class clazz = null;

    public YxMapperFactory(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getObject() throws BeansException {
        if (this.clazz.getName().endsWith("Mapper")) {
            //这是mybatis的代理类，具体可参考《mybatis的本质和原理》
            //return YxSession.dealSql(clazz);
            return null;
        }
        return null;
    }
}
