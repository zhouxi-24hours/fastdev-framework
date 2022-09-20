package com.fastdev.limit.starter.algorithm;

import com.fastdev.exception.BaseException;
import com.fastdev.limit.starter.constant.RateLimitConstants;
import com.fastdev.limit.starter.keys.CheckTypeContext;
import com.fastdev.limit.starter.modes.TokenBucketParam;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * 令牌桶算法
 * @author zhouxi
 * @className TokenBucketAlgorithm
 * @date 2022/9/5 20:50
 **/
public class TokenBucketAlgorithm implements ModeAlgorithm<TokenBucketParam> {

    @Lazy
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean tryAcquire(TokenBucketParam param) throws BaseException {
        String uniqueKey = CheckTypeContext.getInstance().getCheckTypeKey(param.getCheckType()).getUniqueKey();
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(uniqueKey);
        if(!rateLimiter.isExists()) {
            // 设置速率
            rateLimiter.trySetRate(RateType.OVERALL, param.getBucketCapacity(), param.getTokenBucketTimeInterval(), RateIntervalUnit.SECONDS);
        } else if(rateLimiter.isExists() && isNotEqual(param, rateLimiter)) {
            rateLimiter.delete();
            rateLimiter.trySetRate(RateType.OVERALL, param.getBucketCapacity(), param.getTokenBucketTimeInterval(), RateIntervalUnit.SECONDS);
        }
        return rateLimiter.tryAcquire(1);
    }

    /**
     * 判断参数是否相等
     * @param param
     * @param rateLimiter
     * @return
     */
    public boolean isNotEqual(TokenBucketParam param, RRateLimiter rateLimiter) {
        if(rateLimiter.getConfig().getRate() != param.getBucketCapacity()) {
            return true;
        }

        if(rateLimiter.getConfig().getRateInterval() != (param.getTokenBucketTimeInterval() * RateLimitConstants.VALUE_1000)) {
            return true;
        }

        return false;
    }
}
