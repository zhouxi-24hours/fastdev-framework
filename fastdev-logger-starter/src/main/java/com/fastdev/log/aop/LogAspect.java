package com.fastdev.log.aop;

import com.fastdev.common.util.ThreadUtils;
import com.fastdev.log.annotation.IgnoreLog;
import com.fastdev.log.domain.SystemLogStrategy;
import com.fastdev.log.util.IpUtils;
import com.fastdev.log.util.JsonUtils;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author zhouxi
 * @className logAsperct
 * @date 2022/9/19 9:30
 **/
@Aspect
@Component
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private static final String STANDARD_MULTIPART_FILE = "StandardMultipartFile";

    /**
     * 切点
     */
    @Pointcut("execution(* *..controller..*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object doInvoke(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        result = pjp.proceed();
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        printLog(pjp, result, elapsedTime);
        return result;
    }

    /**
     * 方法抛出异常后
     *
     * @param pjp
     * @param ex
     */
    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void doServiceAfterThrowing(final JoinPoint pjp, Exception ex) {
        try {
            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            printLog(pjp, new Object(), elapsedTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印日志
     *
     * @param pjp         连接点
     * @param result      方法调用返回结果
     * @param elapsedTime 方法调用花费时间
     */
    private void printLog(JoinPoint pjp, Object result, long elapsedTime) {
        SystemLogStrategy strategy = getFocus(pjp);

        if (null != strategy) {
            strategy.setThreadId(ThreadUtils.getThreadId());
            String resultStr = JsonUtils.toJSONString(result);
            if (!StringUtils.isEmpty(resultStr) && resultStr.length() <= 100) {
                //不超过100打印，防止数据太多
                strategy.setResult(resultStr);
            }
            strategy.setElapsedTime(elapsedTime);
            strategy.setRequestIp(IpUtils.getIpAddress());
            //skywalking的traceId
            strategy.setTraceId(TraceContext.traceId());
            if (strategy.isAsync()) {
                ThreadUtils.getDefaultThreadPool().execute(() -> LOG.info(strategy.format(), strategy.args()));
            } else {
                LOG.info(strategy.format(), strategy.args());
            }
        }
    }

    /**
     * 获取注解
     */
    private SystemLogStrategy getFocus(JoinPoint pjp) {
        Signature signature = pjp.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Object[] args = pjp.getArgs();
        String targetClassName = pjp.getTarget().getClass().getName();
        try {
            Class<?> clazz = Class.forName(targetClassName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    IgnoreLog ignoreLog = method.getAnnotation(IgnoreLog.class);
                    if (Objects.nonNull(ignoreLog)) {
                        return null;
                    }
                    if (args.length == method.getParameterCount()) {
                        SystemLogStrategy strategy = new SystemLogStrategy();
                        strategy.setClassName(className);
                        strategy.setMethodName(methodName);
                        try {
                            if (args.length > 0 && STANDARD_MULTIPART_FILE.equals(args[0].getClass().getSimpleName())) {
                                //文件上传不做json转换
                                strategy.setArguments(args[0].getClass().getSimpleName());
                            } else {
                                strategy.setArguments(JsonUtils.toJSONString(args));
                            }
                        } catch (Exception e) {
                            LOG.error(e.getMessage());
                        }
                        return strategy;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
