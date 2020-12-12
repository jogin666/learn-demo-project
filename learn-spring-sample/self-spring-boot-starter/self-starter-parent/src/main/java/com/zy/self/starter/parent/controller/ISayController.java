package com.zy.self.starter.parent.controller;

import com.zy.self.spring.template.FirstISayTemplate;
import com.zy.self.spring.template.SecondISayTemplate;
import com.zy.self.spring.template.ThirdISayTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ZY
 * @Date 2020/12/12 14:23
 * @Version 1.0
 */
@RestController
public class ISayController {

    @Autowired
    private FirstISayTemplate firstISayTemplate;
    @Autowired
    private SecondISayTemplate secondISayTemplate;
    @Autowired
    private ThirdISayTemplate thirdISayTemplate;

    @RequestMapping("/firstSay")
    public String firstSay(){
        return firstISayTemplate.iSay();
    }


    @RequestMapping("/secondSay")
    public String secondSay(@RequestParam("name")String name){
        return secondISayTemplate.iSay(name);
    }

    @RequestMapping("/thirdSay")
    public String thirdSay(@RequestParam("name")String name){
        return thirdISayTemplate.iSay(name);
    }
}
