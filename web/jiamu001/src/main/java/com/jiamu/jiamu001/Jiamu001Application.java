package com.jiamu.jiamu001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.jiamu.jiamu001.mapper")
public class Jiamu001Application {

    public static void main(String[] args) {
        SpringApplication.run(Jiamu001Application.class, args);
    }

}
