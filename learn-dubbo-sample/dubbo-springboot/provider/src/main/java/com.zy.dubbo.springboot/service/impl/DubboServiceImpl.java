package com.zy.dubbo.springboot.service.impl;

import com.zy.dubbo.service.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author: jogin
 * @date: 2020/9/12 22:04
 */
@Service
@Component
public class DubboServiceImpl implements DubboService {

    @Override
    public String say(String name) {
        return "hello " + name;
    }
}
