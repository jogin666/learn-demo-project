package com.zy.self.spring.autoconfigure;

import com.zy.self.spring.template.ThirdISayTemplate;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author ZY
 * @Date 2020/12/12 13:59
 * @Version 1.0
 */
public class ISayServiceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(ThirdISayTemplate.class);
        registry.registerBeanDefinition("thirdISayTemplate",beanDefinition);
    }
}
