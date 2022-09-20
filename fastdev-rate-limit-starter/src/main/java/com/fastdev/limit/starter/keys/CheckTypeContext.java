package com.fastdev.limit.starter.keys;

import com.fastdev.limit.starter.enums.CheckType;

import java.util.Objects;

/**
 * @author zhouxi
 * @className CheckTypeContext
 * @date 2022/9/6 8:57
 **/
public class CheckTypeContext {

    private static CheckTypeContext checkTypeContext;

    private CheckTypeContext() {}

    public synchronized static CheckTypeContext getInstance() {
        if(Objects.isNull(checkTypeContext)) {
            synchronized (CheckTypeContext.class) {
                if(Objects.isNull(checkTypeContext)) {
                    checkTypeContext = new CheckTypeContext();
                }
            }
        }
        return checkTypeContext;
    }

    public CheckTypeKey getCheckTypeKey(CheckType checkType) {
        CheckTypeKey checkTypeKey = null;
        switch (checkType) {
            case IP: {
                checkTypeKey = new IpTypeKey();
                break;
            }
            default: checkTypeKey = new IpTypeKey();
        }
        return checkTypeKey;
    }
}
