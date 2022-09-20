package com.fastdev.common.util;

/**
 * 状态码
 * @author zhouxi
 */
public enum HttpStatus {
    /**
     * value:msg
     */
    OK(200, "请求成功"),
    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "拒绝访问"),
    NOT_FOUND(404, "请求资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务端错误"),
    NOT_IMPLEMENTED(501, "为实现"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    VALID_FAIL(1000, "参数校验失败"),
    NOT_MENU_AUTH(1001, "无菜单权限"),
    PROHIBITED_MULTI_LOGIN(1002, "禁止账号多端登录"),
    SERVICE_CALL_FAIL(2000, "服务调用失败");

    private final Integer value;

    private final String msg;

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    HttpStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }
}
