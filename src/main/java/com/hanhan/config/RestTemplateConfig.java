package com.hanhan.config;

import com.hanhan.currentLimiting.TokenBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public TokenBucket tokenBucket(){
        return new TokenBucket(10000D, 1D);
    }

}
