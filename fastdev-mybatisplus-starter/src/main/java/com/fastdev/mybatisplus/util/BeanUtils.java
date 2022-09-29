package com.fastdev.mybatisplus.util;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Bean相关处理工具类
 *
 * @author zhouxi
 */
public class BeanUtils {

    /***
     * 缓存BeanCopier
     */
    private static final Map<String, BeanCopier> beanCopierInstMap = new ConcurrentHashMap<>();

    /***
     * 获取实例
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier getBeanCopierInstance(Object source, Object target) {
        //build key
        String beanCopierKey = source.getClass().toString() + "_" + target.getClass().toString();
        BeanCopier copierInst = beanCopierInstMap.get(beanCopierKey);
        if (copierInst == null) {
            copierInst = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierInstMap.put(beanCopierKey, copierInst);
        }
        return copierInst;
    }

    /**
     * Copy属性到另一个对象
     *
     * @param source
     * @param target
     */
    public static Object copyProperties(Object source, Object target) {
        BeanCopier copierInst = getBeanCopierInstance(source, target);
        copierInst.copy(source, target, null);
        return target;
    }

    /***
     * 将对象转换为另外的对象实例
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            target = clazz.getConstructor().newInstance();
            copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("对象转换异常, class={}"+clazz.getName());
        }
        return target;
    }

    /***
     * 将对象转换为另外的对象实例
     * @param source
     * @param clazz
     * @param <T>
     * @param consumer 可以做一些额外的数据处理，如没法映射属性的处理
     * @return
     */
    public static <T> T convert(Object source, Class<T> clazz, Consumer<T> consumer) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            target = clazz.getConstructor().newInstance();
            copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("对象转换异常, class={}"+clazz.getName());
        }
        consumer.accept(target);
        return target;
    }

    /***
     * 将对象转换为另外的对象实例
     * @param sourceList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(List sourceList, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>();
        // 类型相同，直接跳过
        if (clazz.getName().equals(sourceList.get(0).getClass().getName())) {
            return sourceList;
        }
        // 不同，则转换
        try {
            for (Object source : sourceList) {
                T target = clazz.getConstructor().newInstance();
                copyProperties(source, target);
                resultList.add(target);
            }
        } catch (Exception e) {
            throw new RuntimeException("对象转换异常, class={}"+clazz.getName());
        }
        return resultList;
    }

    /***
     * 将对象转换为另外的对象实例
     * @param sourceList
     * @param clazz
     * @param <T>
     * @param consumer 可以做一些额外的数据处理，如没法映射属性的处理
     * @return
     */
    public static <T> List<T> convertList(List sourceList, Class<T> clazz, Consumer<T> consumer) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>();
        // 类型相同，直接跳过
        if (clazz.getName().equals(sourceList.get(0).getClass().getName())) {
            return sourceList;
        }
        // 不同，则转换
        try {
            for (Object source : sourceList) {
                T target = clazz.getConstructor().newInstance();
                copyProperties(source, target);
                consumer.accept(target);
                resultList.add(target);
            }
        } catch (Exception e) {
            throw new RuntimeException("对象转换异常, class={}"+clazz.getName());
        }
        return resultList;
    }
}
