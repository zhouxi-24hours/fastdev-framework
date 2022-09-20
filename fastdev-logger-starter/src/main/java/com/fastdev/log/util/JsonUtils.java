package com.fastdev.log.util;


import cn.hutool.json.JSONUtil;

/**
 * @ClassName JsonUtil
 * @Description JsonUtil
 * @Author Fly
 * @Version
 * @Date 2021/1/13 9:45
 */
public class JsonUtils {

    public static String toJSONString(Object object) {
        return JSONUtil.toJsonStr(object);
    }
}
