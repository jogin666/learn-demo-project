package com.zy.self.enable.analyser.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ZY
 * @Date 2020/12/12 15:18
 * @Version 1.0
 */
public class BeanFactory {

    private final ConcurrentHashMap<String,Object> beanFactory = new ConcurrentHashMap<>(16);

    public void registry(String beanName,Object bean){
        beanFactory.putIfAbsent(beanName,bean);
    }

    public Object get(String beanName){
        return beanFactory.get(beanName);
    }
}
