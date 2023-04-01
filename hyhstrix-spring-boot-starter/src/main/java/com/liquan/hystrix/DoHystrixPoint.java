package com.liquan.hystrix;

import com.liquan.hystrix.annotation.DoHystrix;
import com.liquan.hystrix.valve.IValveService;
import com.liquan.hystrix.valve.impl.ValveServiceImpl;
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
 * @Date: 2023/3/29 19:39
 * @author: LiQuan
 **/
@Aspect
@Component
public class DoHystrixPoint {

    @Pointcut("@annotation(com.liquan.hystrix.annotation.DoHystrix)")
    public void aopPoint(){

    }

    @Around("aopPoint() && @annotation(doGovern)")
    public Object doRouter(ProceedingJoinPoint jp, DoHystrix doGovern) throws NoSuchMethodException {
        IValveService service=new ValveServiceImpl();
        return service.access(jp, getMethod(jp), doGovern, jp.getArgs());
    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Signature signature= jp.getSignature();
        MethodSignature methodSignature= (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }


}
