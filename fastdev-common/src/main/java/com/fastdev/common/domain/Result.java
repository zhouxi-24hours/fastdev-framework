package com.fastdev.common.domain;

import com.fastdev.common.util.HttpStatus;

import java.io.Serializable;

/**
 * @ClassName Result
 * @author zhouxi
 * @Date 2020/10/22 10:51
 */
public class Result<T> implements Serializable {

    private Integer code;

    private T data;

    private String msg;

    public Result() {}

    public Result(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(HttpStatus.OK.getValue(),data,"操作成功");
    }

    public static <T> Result<T> success(T data, String msg){
        return new Result<T>(HttpStatus.OK.getValue(),data,msg);
    }

    public static <T> Result<T> success(String msg){
        return new Result<T>(HttpStatus.OK.getValue(),null,msg);
    }

    public static <T> Result<T> success(){
        return new Result<>(HttpStatus.OK.getValue(),null,"操作成功");
    }

    public static<T> Result<T> fail(String msg, T data){
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.getValue(),data,msg);
    }
    public static<T> Result<T> fail(T data){
        return fail("操作失败",data);
    }

    public static<T> Result<T> fail(String msg){
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.getValue(),null,msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
