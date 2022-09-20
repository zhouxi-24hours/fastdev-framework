package com.fastdev.limit.starter.aop;

import com.fastdev.exception.BaseException;
import com.fastdev.limit.starter.algorithm.ModeAlgorithm;
import com.fastdev.limit.starter.annotation.MtRateLimit;
import com.fastdev.limit.starter.modes.BaseParam;
import com.fastdev.limit.starter.modes.ParamContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhouxi
 * @className MtRateLimitAspect
 * @date 2022/9/6 9:52
 **/
@Slf4j
@Aspect
public class MtRateLimitAspect {

    private final Map<String, ModeAlgorithm> modeAlgorithmMap;

    public MtRateLimitAspect(Map<String, ModeAlgorithm> modeAlgorithmMap) {
        this.modeAlgorithmMap = modeAlgorithmMap;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.fastdev.limit.starter.annotation.MtRateLimit)")
    public void mtRateLimitAspectMethod() {}

    /**
     * 方法执行前
     * @param joinPoint
     */
    @Before(value = "mtRateLimitAspectMethod()")
    public void doServiceBefore(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MtRateLimit mtRateLimit = method.getAnnotation(MtRateLimit.class);
        BaseParam param = ParamContext.getInstance().getParam(mtRateLimit);
        ModeAlgorithm modeAlgorithm = modeAlgorithmMap.get(mtRateLimit.mode().getValue());
        if(Objects.isNull(modeAlgorithm)) {
            throw new BaseException("限流组件找不到算法实现类");
        }
        if(!modeAlgorithm.tryAcquire(param)) {
            throw new BaseException(mtRateLimit.throwsErrorMsg());
        }
    }
}
