package com.liquan.ratelimiter;

import com.liquan.ratelimiter.annotation.DoRateLimiter;
import com.liquan.ratelimiter.valve.IValveService;
import com.liquan.ratelimiter.valve.impl.RateLimiterValve;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Date: 2023/3/29 22:02
 * @author: LiQuan
 **/
@Aspect
@Component
public class DoRateLimiterPoint {

    @Pointcut("@annotation(com.liquan.ratelimiter.annotation.DoRateLimiter)")
    public void aopPoint(){

    }

    @Around("aopPoint()&&@annotation(doRateLimiter)")
    public Object doRouter(ProceedingJoinPoint jp, DoRateLimiter doRateLimiter) throws Throwable {
        IValveService service=new RateLimiterValve();
        return service.access(jp, getMethod(jp), doRateLimiter, jp.getArgs());
    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Signature signature= jp.getSignature();
        MethodSignature methodSignature= (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }
}
