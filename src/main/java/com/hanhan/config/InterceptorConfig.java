package com.hanhan.config;

import com.hanhan.currentLimiting.TokenBucket;
import com.hanhan.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //定义此类为配置文件(即相当于之前的xml配置文件)
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenBucket tokenBucket;

    //相当于mvc:interceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //要拦截user下的所有访问请求，必须用户登录后才可访问
        //但是这样拦截的路径中会有一些是不需要用户登录也可访问的
        //addPathPatterns为要拦截的路径
        String[] addPathPatterns ={
                "/key/addKey", "/key/addCipherText", "/key/GetCipherText"
        };

        //相当于mvc:interceptor bean class=""
        registry.addInterceptor(new UserInterceptor(tokenBucket))
                .addPathPatterns(addPathPatterns);
    }
}
