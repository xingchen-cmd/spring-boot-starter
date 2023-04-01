package com.liquan.whitelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Date: 2023/3/28 16:58
 * @author: LiQuan
 **/
@ConfigurationProperties("whitelist")//用注解用于创建前缀为quan.whiteList的自定义配置信息，就可以读取yml或者properties的配置信息
public class WhiteListProperties {
    private String user;

    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }
}
