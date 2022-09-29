package com.fastdev.mybatisplus.pojo.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * @ClassName LambdaQuery
 * @Description LambdaQuery
 * @author zhouxi
 */
public class LambdaQuery<T> extends AbstractQuery<T, SFunction<T, ?>, LambdaQuery<T>, LambdaQueryWrapper<T>>{

    public LambdaQuery(LambdaQueryWrapper wrapper) {
        this.wrapperChildren = wrapper;
    }

    public LambdaQuery() {}

    public LambdaQueryWrapper<T> getLambdaQueryWrapper(){
        return this.wrapperChildren;
    }
}
