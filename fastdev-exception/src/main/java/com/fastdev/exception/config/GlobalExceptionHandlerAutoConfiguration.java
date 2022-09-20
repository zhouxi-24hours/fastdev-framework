package com.fastdev.exception.config;

import com.fastdev.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName GlobalExceptionHandlerAutoConfiguration
 * @Description 全局异常处理自动装配
 * @author zhouxi
 */
@Configuration
public class GlobalExceptionHandlerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
