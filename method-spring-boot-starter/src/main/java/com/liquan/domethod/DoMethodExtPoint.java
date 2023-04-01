package com.liquan.domethod;

import com.alibaba.fastjson2.JSON;
import com.liquan.domethod.annotation.DoMethodExt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;



/**
 * @Description:
 * @Date: 2023/3/29 22:59
 * @author: LiQuan
 **/
@Aspect
@Component
public class DoMethodExtPoint {

    private Logger logger= LoggerFactory.getLogger(DoMethodExtPoint.class);

    @Pointcut("@annotation(com.liquan.domethod.annotation.DoMethodExt)")
    public void aopPoint(){

    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        //获取内容
        Method method=getMethod(jp);
        DoMethodExt doMethodExt=method.getAnnotation(DoMethodExt.class);
        //获取拦截方法
        String methodName=doMethodExt.mothod();

        //功能处理
        Method methodExt=getClass(jp).getMethod(methodName, method.getParameterTypes());
        Class<?> returnType=methodExt.getReturnType();

        if(!returnType.getName().equals("boolean")){
            throw  new RuntimeException("annotation @DoMethod set method "+methodName+" returnType in not boolean ");
        }
        //拦截判断正常
        boolean invoke=(boolean) methodExt.invoke(jp.getThis(), jp.getArgs());

        return invoke? jp.proceed(): JSON.parseObject(doMethodExt.returnJson(),method.getReturnType());

    }

    private Class<? extends Object> getClass(ProceedingJoinPoint jp) {
        return jp.getTarget().getClass();
    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Signature signature=jp.getSignature();
        MethodSignature methodSignature= (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}
