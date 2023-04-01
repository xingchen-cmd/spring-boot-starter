package com.liquan.whitelist.config;

import com.liquan.whitelist.DoJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Date: 2023/3/28 17:04
 * @author: LiQuan
 **/
@Configuration
@ConditionalOnClass({com.liquan.whitelist.config.WhiteListProperties.class,DoJoinPoint.class})
@EnableConfigurationProperties(WhiteListProperties.class)
public class WhiteListAutoConfigration {

    @Bean("whiteListConfig")
    @ConditionalOnMissingBean
    public String whiteListConfig(WhiteListProperties properties){
        return properties.getUser();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public DoJoinPoint getDoJoinPoint() {
//        return new DoJoinPoint();
//    }

}
