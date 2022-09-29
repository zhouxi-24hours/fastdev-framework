package com.fastdev.mybatisplus.pojo.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @ClassName NormalQuery
 * @Description NormalQuery
 * @author zhouxi
 */
public class NormalQuery<T> extends AbstractQuery<T, String, NormalQuery<T>, QueryWrapper<T>> {

    public NormalQuery(QueryWrapper wrapper) {
        this.wrapperChildren = wrapper;
    }
}
