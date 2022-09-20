package com.fastdev.limit.starter.modes;

import com.fastdev.limit.starter.annotation.MtRateLimit;

import java.util.Objects;

/**
 * @author zhouxi
 * @className ParamContext
 * @date 2022/9/6 10:12
 **/
public class ParamContext {

    private static ParamContext paramContext;

    private ParamContext() {}

    public synchronized static ParamContext getInstance() {
        if(Objects.isNull(paramContext)) {
            synchronized (ParamContext.class) {
                if(Objects.isNull(paramContext)) {
                    paramContext = new ParamContext();
                }
            }
        }
        return paramContext;
    }

    public BaseParam getParam(MtRateLimit mtRateLimit) {
        BaseParam param = null;
        switch (mtRateLimit.mode()) {
            case TOKEN_BUCKET: {
                param = getTokenBucketParam(mtRateLimit);
                break;
            }
            default: param = getTokenBucketParam(mtRateLimit);
        }
        return param;
    }

    private TokenBucketParam getTokenBucketParam(MtRateLimit mtRateLimit) {
        TokenBucketParam param = new TokenBucketParam();
        param.setCheckType(mtRateLimit.checkType());
        param.setTokenBucketTimeInterval(mtRateLimit.tokenBucketTimeInterval());
        param.setBucketCapacity(mtRateLimit.bucketCapacity());
        return param;
    }
}
