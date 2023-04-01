package com.liquan.hystrix.valve;

import com.liquan.hystrix.annotation.DoHystrix;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Date: 2023/3/28 20:54
 * @author: LiQuan
 **/
public interface IValveService {

    Object access(ProceedingJoinPoint jp, Method method, DoHystrix doHystrix,Object[] args);
}
