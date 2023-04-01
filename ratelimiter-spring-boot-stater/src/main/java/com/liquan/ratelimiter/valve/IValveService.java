package com.liquan.ratelimiter.valve;

import com.liquan.ratelimiter.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Date: 2023/3/29 21:41
 * @author: LiQuan
 **/
public interface IValveService {
    Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter,Object[] args) throws Throwable;
}
