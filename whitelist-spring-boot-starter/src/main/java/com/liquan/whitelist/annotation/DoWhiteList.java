package com.liquan.whitelist.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @Description: 白名单注解
 * @Date: 2023/3/28 16:50
 * @author: LiQuan
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface DoWhiteList {
    /**
     * 从入参提取的属性（区别是否为白名单的key）
     * @return
     */
    String key() default "";

    /**
     * 拦截到用户请求之后给的一个返回结果
     * @return
     */
    String returnJson() default "";
}
