package com.fastdev.limit.starter.algorithm;

import com.fastdev.exception.BaseException;
import com.fastdev.limit.starter.modes.BaseParam;

/**
 * @author zhouxi
 * @className ModeAlrm
 * @date 2022/9/5 20:44
 **/
public interface ModeAlgorithm<T extends BaseParam> {

    /**
     * 是否允许请求接口
     * @param t
     * @return
     * @throws BaseException
     */
    boolean tryAcquire(T t) throws BaseException;
}