package com.zy.self.spring.autoconfigure;

import com.zy.self.spring.template.FirstISayTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ZY
 * @Date 2020/12/12 14:02
 * @Version 1.0
 */

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ISayServiceProperties.class)
public class ISayServiceAutoConfiguration {

    @Bean
    public FirstISayTemplate firstISayTemplate(ISayServiceProperties properties){
        return new FirstISayTemplate(properties);
    }
}
