package com.zy.self.spring.template;

import com.zy.self.isay.ISayService;
import com.zy.self.spring.autoconfigure.ISayServiceProperties;

/**
 * @Author ZY
 * @Date 2020/12/12 14:05
 * @Version 1.0
 */
public class FirstISayTemplate {

    private ISayService sayService = new ISayService();

    public FirstISayTemplate(ISayServiceProperties properties){
        sayService.setFix(properties.getFix());
        sayService.setPre(properties.getPre());
    }

    public String iSay(){
        return sayService.iSay();
    }
}
