package com.fastdev.common.util;

import cn.hutool.core.thread.ThreadUtil;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName ThreadUtil
 * @author zhouxi
 * @Date 2021/1/13 9:46
 */
public class ThreadUtils {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getThreadId() {
        String threadId = threadLocal.get();
        if (null == threadId) {
            threadId = UUID.randomUUID().toString();
            threadLocal.set(threadId);
        }
        return threadId;
    }

    /**
     * 获取默认的线程池,核心线程5个，最大线程10个
     * @return
     */
    public static ThreadPoolExecutor getDefaultThreadPool() {
        return ThreadUtil.newExecutor(5, 10);
    }
}
