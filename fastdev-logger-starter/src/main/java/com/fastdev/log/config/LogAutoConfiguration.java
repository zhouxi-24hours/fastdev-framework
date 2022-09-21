package com.fastdev.log.config;

import com.fastdev.log.aop.LogAspect;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName LogAutoConfiguration
 * @Date 2021/1/13 10:59
 * @author zhouxi
 */
@Configuration
@AutoConfigureOrder(Integer.MAX_VALUE)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnClass(LogAspect.class)
@ConditionalOnMissingBean(LogAspect.class)
public class LogAutoConfiguration {

    @Bean
    public LogAspect LogAspect() {
        return new LogAspect();
    }
}
