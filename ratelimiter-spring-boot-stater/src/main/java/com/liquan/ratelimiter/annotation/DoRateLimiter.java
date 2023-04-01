package com.liquan.ratelimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Date: 2023/3/29 21:36
 * @author: LiQuan
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoRateLimiter {

    double permitsPerSecond() default 0d; //限流许可量

    String resultJson() default ""; //失败结果
}
