package com.fastdev.log.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @ClassName IgnoreLog
 * @Date 2021/12/21 14:45
 * @author zhouxi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface IgnoreLog {
}
