package com.zy.dubbo.consumer.controller;

import com.zy.dubbo.service.DubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jogin
 * @date: 2020/9/12 22:11
 */
@RestController
public class TestConsumerController {

    @Reference
    private DubboService dubboService;

    @GetMapping("/{name}")
    public String test(@PathVariable("name")String name){
        return dubboService.say(name);
    }
}
