package com.fastdev.limit.starter.keys;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zhouxi
 * @className CheckTypeKey
 * @date 2022/9/5 20:25
 **/
public interface CheckTypeKey {

    /**
     * 获取唯一的Key
     * @return
     */
    String getUniqueKey();

    /**
     * 获取HttpServletRequest
     * @return
     */
    default HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) Objects.
                requireNonNull(RequestContextHolder.getRequestAttributes()));
        return servletRequestAttributes.getRequest();
    }
}