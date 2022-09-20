package com.fastdev.limit.starter.modes;

import com.fastdev.limit.starter.enums.CheckType;

/**
 * @author zhouxi
 * @className BaseParam
 * @date 2022/9/6 8:41
 **/
public abstract class BaseParam {

    /**
     * 限流维度
     */
    private CheckType checkType;

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }
}
