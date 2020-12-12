package com.zy.self.isay;

/**
 * @Author ZY
 * @Date 2020/12/12 13:47
 * @Version 1.0
 */
public class ISayService {

    private String pre;
    private String fix;

    public String iSay() {
        return pre + " autoconfiguration " + fix;
    }

    public String iSay(String name){
        return "@EnableSelfISay: " + name;
    }

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
