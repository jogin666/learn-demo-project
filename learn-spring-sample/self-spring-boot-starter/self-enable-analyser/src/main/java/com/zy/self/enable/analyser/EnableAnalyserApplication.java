package com.zy.self.enable.analyser;

import com.zy.self.enable.analyser.annotation.EnableSelfAnalyser;
import com.zy.self.enable.analyser.annotation.SelfImport;
import com.zy.self.enable.analyser.bean.IBean;
import com.zy.self.enable.analyser.registrar.BeanRegistrar;
import com.zy.self.enable.analyser.registry.BeanFactory;

import java.lang.annotation.*;

/**
 * @Author ZY
 * @Date 2020/12/12 15:08
 * @Version 1.0
 */

@EnableSelfAnalyser
public class EnableAnalyserApplication {

    private static final BeanFactory factory = new BeanFactory();

    public static void main(String[] args) {
        Annotation[] annotations = EnableAnalyserApplication.class.getAnnotations();
        handleCycle(annotations);
        System.out.println(factory.get(IBean.class.getCanonicalName()));
    }

    static void handle(Annotation annotation) {
        if (annotation instanceof SelfImport) {
            try {
                Class<?> clazz = ((SelfImport) annotation).value();
                if (BeanRegistrar.class.isAssignableFrom(clazz)) {
                    BeanRegistrar registrar = (BeanRegistrar) clazz.newInstance();
                    registrar.registryBean(factory);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    static void handleCycle(Annotation[] annotations) {
        if (annotations == null) {
            return;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof Target || annotation instanceof Documented || annotation instanceof Retention
                    || annotation instanceof Inherited || annotation instanceof Repeatable) {
                continue;
            }
            Class<? extends Annotation> type = annotation.annotationType();
            handleCycle(type.getAnnotations());
            handle(annotation);
        }
    }
}
