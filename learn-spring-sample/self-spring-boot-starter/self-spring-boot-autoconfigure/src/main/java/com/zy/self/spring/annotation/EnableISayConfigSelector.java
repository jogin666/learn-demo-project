package com.zy.self.spring.annotation;

import com.zy.self.spring.autoconfigure.ISayServiceImportSelector;
import com.zy.self.spring.autoconfigure.ISayServiceRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author ZY
 * @Date 2020/12/12 14:15
 * @Version 1.0
 */

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ISayServiceImportSelector.class)
public @interface EnableISayConfigSelector {
}
