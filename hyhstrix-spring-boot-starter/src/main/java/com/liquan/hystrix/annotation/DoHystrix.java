package com.liquan.hystrix.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Date: 2023/3/28 20:47
 * @author: LiQuan
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoHystrix {
    String returnJson() default "";   //失败结果JSON

    int timeoutValue() default 0;      //超时熔断
}
