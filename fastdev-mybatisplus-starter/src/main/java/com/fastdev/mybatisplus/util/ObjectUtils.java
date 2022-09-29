package com.fastdev.mybatisplus.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @ClassName ObjectUtils
 * @Description ObjectUtils
 * @author zhouxi
 */
public class ObjectUtils {

    public static boolean nonNull(Object obj) {
        if (obj instanceof String) {
            return StringUtils.isNotBlank((String) obj);
        } else {
            return Objects.nonNull(obj);
        }
    }
}
