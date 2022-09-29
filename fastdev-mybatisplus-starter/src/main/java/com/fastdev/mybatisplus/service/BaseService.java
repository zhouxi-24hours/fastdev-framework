package com.fastdev.mybatisplus.service;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fastdev.mybatisplus.pojo.bo.PageBO;
import com.fastdev.mybatisplus.pojo.query.AbstractQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseService
 * @Description BaseService
 * @author zhouxi
 */
public interface BaseService<T> {

    /**
     * 默认批次提交数量
     */
    int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return
     */
    T selectById(Serializable id);

    /**
     * 查询列表
     *
     * @param abstractQuery
     * @return
     */
    List<T> selectList(AbstractQuery abstractQuery);

    /**
     * 查询列表 带数据权限过滤
     *
     * @param abstractQuery
     * @return
     */
    default List<T> selectListDataScope(AbstractQuery abstractQuery) {
        return Collections.emptyList();
    }

    /**
     * 查询所有
     *
     * @return
     */
    List<T> selectList();

    /**
     * 单个查询
     *
     * @param abstractQuery
     * @return
     */
    T selectOne(AbstractQuery abstractQuery);

    /**
     * 根据id批量查询
     *
     * @param idList
     * @return
     */
    List<T> selectBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param abstractQuery 实体对象封装操作类（可以为 null）
     * @return
     */
    Integer selectCount(AbstractQuery abstractQuery);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param abstractQuery 实体对象封装操作类（可以为 null）
     * @return
     */
    Long selectCountLong(AbstractQuery abstractQuery);
    /**
     * 保存
     *
     * @param entity
     */
    int save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, 1000);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     * @return
     */
    boolean saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     */
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 更新
     *
     * @param entity
     */
    int updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param abstractQuery 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return
     */
    int update(T entity, AbstractQuery abstractQuery);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return
     */
    int deleteById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     * @return
     */
    boolean deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param abstractQuery
     * @return
     */
    boolean delete(AbstractQuery abstractQuery);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return
     */
    int deleteBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList);

    /**
     * 分页查询
     *
     * @param abstractQuery
     * @return
     */
    PageBO<T> selectPage(AbstractQuery abstractQuery);
}
