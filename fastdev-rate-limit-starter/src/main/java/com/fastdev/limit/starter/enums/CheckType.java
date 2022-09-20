package com.fastdev.limit.starter.enums;

/**
 * @author zhouxi
 * @className CheckType
 * @date 2022/9/5 20:09
 **/
public enum CheckType {

    /**
     * IP+用户维度
     */
    IP_USER,

    /**
     * IP限流
     */
    IP,

    /**
     * 用户限流
     */
    USER,

    /**
     * 自定义
     */
    CUSTOM
}