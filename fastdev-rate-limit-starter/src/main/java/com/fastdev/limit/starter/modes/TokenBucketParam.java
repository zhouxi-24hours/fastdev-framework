package com.fastdev.limit.starter.modes;

import lombok.Data;

/**
 * @author zhouxi
 * @className TokenBucketParam
 * @date 2022/9/6 8:44
 **/
@Data
public class TokenBucketParam extends BaseParam {

    /**
     * 向令牌桶中添加数据的时间间隔
     */
    private long tokenBucketTimeInterval;

    /**
     * 每次为令牌桶中添加的令牌数量
     */
    private long bucketCapacity;
}
