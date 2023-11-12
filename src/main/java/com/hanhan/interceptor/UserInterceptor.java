package com.hanhan.interceptor;

import com.hanhan.currentLimiting.TokenBucket;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private TokenBucket tokenBucket;

    public UserInterceptor() {
    }

    public UserInterceptor(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
    }

    //访问controller之前拦截请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return tokenBucket.limit();
    }

    //在请求访问到controller之后，转到视图之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //在请求访问到controller之后，转到视图之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
