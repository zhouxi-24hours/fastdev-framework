package com.fastdev.log.domain;


import java.io.Serializable;

/**
 * @ClassName SystemLogStrategy
 * @author zhouxi
 * @Date 2021/1/13 9:44
 */
public class SystemLogStrategy implements Serializable {

    private boolean async;

    private String threadId;

    private String location;

    private String description;

    private String className;

    private String methodName;

    private String arguments;

    private String result;

    private Long elapsedTime;

    private String requestIp;

    private String traceId;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String format() {
        return "time: {}ms,traceId: {},args: {}, result: {},ip: {},threadId: {}, class: {}, method: {} ";
    }

    public Object[] args() {
        return new Object[]{this.elapsedTime, this.traceId, this.arguments, this.result, this.requestIp, this.threadId, this.className, this.methodName};
    }
}
