package com.zy.self.spring.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author ZY
 * @Date 2020/12/12 13:51
 * @Version 1.0
 */
@Component
@ConfigurationProperties("i.say")
public class ISayServiceProperties {

    private String pre;

    private String fix;

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getFix() {
        return fix;
    }

    public void setFix(String fix) {
        this.fix = fix;
    }
}
