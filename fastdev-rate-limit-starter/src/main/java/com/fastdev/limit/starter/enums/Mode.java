package com.fastdev.limit.starter.enums;

import lombok.Getter;

/**
 * 支持的算法
 * @author zhouxi
 * @className Mode
 * @date 2022/9/5 19:53
 **/
@Getter
public enum Mode {

    /**
     * 令牌桶模式
     */
    TOKEN_BUCKET("tokenBucket");

    private String value;

    Mode(String value) {
        this.value = value;
    }
}
