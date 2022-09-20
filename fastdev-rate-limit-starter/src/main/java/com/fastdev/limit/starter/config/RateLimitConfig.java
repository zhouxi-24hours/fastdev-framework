package com.fastdev.limit.starter.config;

import com.fastdev.limit.starter.algorithm.ModeAlgorithm;
import com.fastdev.limit.starter.algorithm.TokenBucketAlgorithm;
import com.fastdev.limit.starter.aop.MtRateLimitAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

/**
 * @author zhouxi
 * @className RateLimitConfig
 * @date 2022/9/5 19:27
 **/
public class RateLimitConfig {

    @Bean
    @DependsOn("tokenBucket")
    public MtRateLimitAspect mtRateLimitAspect(ApplicationContext applicationContext) {
        Map<String, ModeAlgorithm> strategyMap = applicationContext.getBeansOfType(ModeAlgorithm.class);
        return new MtRateLimitAspect(strategyMap);
    }

    @Bean("tokenBucket")
    public TokenBucketAlgorithm tokenBucketAlgorithm() {
        return new TokenBucketAlgorithm();
    }
}
