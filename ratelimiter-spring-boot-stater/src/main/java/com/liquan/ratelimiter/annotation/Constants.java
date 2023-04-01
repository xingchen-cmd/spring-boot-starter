package com.liquan.ratelimiter.annotation;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Date: 2023/3/29 21:39
 * @author: LiQuan
 **/
public class Constants {
    public final static Map<String,RateLimiter> rateLimiterMap= Collections.synchronizedMap(new HashMap<String, RateLimiter>());
}
