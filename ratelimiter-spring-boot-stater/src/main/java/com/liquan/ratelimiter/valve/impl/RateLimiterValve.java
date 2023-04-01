package com.liquan.ratelimiter.valve.impl;

import com.alibaba.fastjson2.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.liquan.ratelimiter.annotation.Constants;
import com.liquan.ratelimiter.annotation.DoRateLimiter;
import com.liquan.ratelimiter.valve.IValveService;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Date: 2023/3/29 21:43
 * @author: LiQuan
 **/
public class RateLimiterValve implements IValveService {
    @Override
    public Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        if(0== doRateLimiter.permitsPerSecond()){
            return jp.proceed();
        }

        String clazzName=jp.getTarget().getClass().getName();
        String methodName=method.getName();

        String key=clazzName+"."+methodName;

        if(null == Constants.rateLimiterMap.get(key)){
            Constants.rateLimiterMap.put(key, RateLimiter.create(doRateLimiter.permitsPerSecond()));
        }

        RateLimiter rateLimiter=Constants.rateLimiterMap.get(key);

        if(rateLimiter.tryAcquire()){
            return jp.proceed();
        }

        return JSON.parseObject(doRateLimiter.resultJson(),method.getReturnType());

    }
}
