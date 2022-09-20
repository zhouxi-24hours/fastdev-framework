package com.fastdev.limit.starter.keys;


import com.fastdev.limit.starter.constant.RateLimitConstants;
import com.fastdev.limit.starter.util.RateLimitUtils;

/**
 * IP模式
 * @author zhouxi
 * @className IPTypeKey
 * @date 2022/9/5 20:26
 **/
public class IpTypeKey implements CheckTypeKey {

    @Override
    public String getUniqueKey() {
        return RateLimitConstants.COMMON_START + RateLimitUtils.getRequestIP(getHttpServletRequest()) + getHttpServletRequest().getRequestURI();
    }
}
