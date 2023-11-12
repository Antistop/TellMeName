package com.hanhan.tellmename;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hanhan"})
@MapperScan(value = "com.hanhan.mapper")
@ServletComponentScan(basePackages = "com.hanhan.filter") //扫描这个包下的注解
public class TellMeNameApplication {

    public static void main(String[] args) {
         SpringApplication.run(TellMeNameApplication.class, args);
    }

}
