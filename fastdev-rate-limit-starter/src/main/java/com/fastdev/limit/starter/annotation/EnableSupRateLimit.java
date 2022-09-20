package com.fastdev.limit.starter.annotation;

import com.fastdev.limit.starter.config.RateLimitConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启限流注解
 * @author zhouxi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {RateLimitConfig.class})
public @interface EnableSupRateLimit {


}
