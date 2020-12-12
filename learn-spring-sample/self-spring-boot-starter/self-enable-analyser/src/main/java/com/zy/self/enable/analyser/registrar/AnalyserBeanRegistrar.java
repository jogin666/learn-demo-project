package com.zy.self.enable.analyser.registrar;

import com.zy.self.enable.analyser.bean.IBean;
import com.zy.self.enable.analyser.registry.BeanFactory;

/**
 * @Author ZY
 * @Date 2020/12/12 15:12
 * @Version 1.0
 */
public class AnalyserBeanRegistrar implements BeanRegistrar{

    @Override
    public void registryBean(BeanFactory factory) {
        IBean bean = new IBean();
        factory.registry(IBean.class.getCanonicalName(),bean);
    }
}
