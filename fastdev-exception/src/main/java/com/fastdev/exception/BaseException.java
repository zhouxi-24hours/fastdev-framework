package com.fastdev.exception;

import com.fastdev.common.domain.Result;

/**
 * @author zhouxi
 * @className BaseException
 * @date 2022/9/15 19:51
 **/
public class BaseException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 消息堆栈
     */
    private Object messageStack;


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public Object getMessageStack() {
        return messageStack;
    }


    public void setMessageStack(Object messageStack) {
        this.messageStack = messageStack;
    }


    public BaseException() {
    }

    public BaseException(String message) {
        this(Result.fail(message, null));
    }

    public BaseException(Result result) {
        this(result.getCode(), result.getMsg(), result.getData());
    }

    public BaseException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(Integer status, String message, Object msgStack) {
        this(status, message);
        this.messageStack = msgStack;
    }
}
