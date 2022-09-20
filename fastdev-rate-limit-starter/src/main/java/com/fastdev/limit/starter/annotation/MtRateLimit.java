package com.fastdev.limit.starter.annotation;

import com.fastdev.limit.starter.enums.CheckType;
import com.fastdev.limit.starter.enums.Mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxi
 * @className MtRateLimit
 * @date 2022/9/5 19:49
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MtRateLimit {

    /**
     * 限流算法；默认令牌桶模式
     * @return
     */
    Mode mode() default Mode.TOKEN_BUCKET;

    /**
     * 限流的维度
     * @return
     */
    CheckType checkType() default CheckType.IP;

    /**
     * 限流报错信息
     * @return
     */
    String throwsErrorMsg() default "请求次数过于频繁，请稍后重试";

    // --------------------------- 令牌桶模式参数 ---------------------------

    /**
     *
     * @return 向令牌桶中添加数据的时间间隔,以秒为单位。默认值1秒
     */
    long tokenBucketTimeInterval() default 1;

    /**
     * @return 每次为令牌桶中添加的令牌数量。默认值2个
     */
    long bucketCapacity() default 2;
}
