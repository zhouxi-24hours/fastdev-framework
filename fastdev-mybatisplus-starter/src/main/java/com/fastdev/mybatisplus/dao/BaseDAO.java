package com.fastdev.mybatisplus.dao;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastdev.mybatisplus.pojo.bo.PageBO;
import com.fastdev.mybatisplus.pojo.query.AbstractQuery;
import com.fastdev.mybatisplus.util.ConvertUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseDAO
 * @Description BaseDAO
 * @Author Fly
 * @Version
 * @Date 2021/1/29 10:12
 */
public interface BaseDAO<T> extends BaseMapper<T> {

    /**
     * 自定义分页查询
     *
     * @param abstractQuery
     * @param dClass
     * @return
     */
    default <D> PageBO<D> selectCustomPage(AbstractQuery abstractQuery, Class<D> dClass) {
        Page<Map<String, Object>> mapPage = selectCustomPage(abstractQuery.getPage(), abstractQuery.getAbstractWrapper());
        return ConvertUtils.pageMapToPageBO(mapPage, dClass);
    }

    /**
     * 自定义列表查询
     *
     * @param abstractQuery
     * @param dClass
     * @param <D>
     * @return
     */
    default <D> List<D> selectCustomList(AbstractQuery abstractQuery, Class<D> dClass) {
        List<Map<String, Object>> mapList = selectCustomList(abstractQuery.getAbstractWrapper());
        List<D> doList = JSONUtil.toList(JSONUtil.toJsonStr(mapList), dClass);
        return doList;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<Map<String, Object>> selectCustomPage(Page page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 自定义列表查询
     *
     * @param queryWrapper
     * @return
     */
    List<Map<String, Object>> selectCustomList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
