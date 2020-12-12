package com.zy.self.starter.parent;

import com.zy.self.spring.annotation.EnableISay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableISay
@SpringBootApplication
public class SelfStarterParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfStarterParentApplication.class, args);
    }

}
