package com.zy.self.spring.template;

import com.zy.self.isay.ISayService;

/**
 * @Author ZY
 * @Date 2020/12/12 14:05
 * @Version 1.0
 */
public class SecondISayTemplate {

    private ISayService sayService = new ISayService();

    public String iSay(String name){
        return sayService.iSay(name);
    }
}
