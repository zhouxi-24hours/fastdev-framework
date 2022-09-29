package com.fastdev.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fastdev.mybatisplus.dao.BaseDAO;
import com.fastdev.mybatisplus.pojo.bo.PageBO;
import com.fastdev.mybatisplus.pojo.query.AbstractQuery;
import com.fastdev.mybatisplus.service.BaseService;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @ClassName MysqlBaseServiceImpl
 * @Description MysqlBaseServiceImpl
 * @author zhouxi
 */
public class BaseServiceImpl<T, D extends BaseDAO<T>> implements BaseService<T> {

    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    private D dao;

    protected Class<?> entityClass = currentModelClass();

    protected Class<?> mapperClass = currentMapperClass();

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), BaseServiceImpl.class, 0);
    }

    protected Class<T> currentMapperClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), BaseServiceImpl.class, 1);
    }

    /**
     * 获取dao
     *
     * @return
     */
    public D getDao() {
        return this.dao;
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    @Override
    public T selectById(Serializable id) {
        return dao.selectById(id);
    }

    /**
     * 查询列表
     *
     * @param abstractQuery
     * @return
     */
    @Override
    public List<T> selectList(AbstractQuery abstractQuery) {
        if (Objects.isNull(abstractQuery)) {
            return Collections.emptyList();
        }
        return dao.selectList(abstractQuery.getAbstractWrapper());
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<T> selectList() {
        return dao.selectList(null);
    }

    /**
     * 单个查询
     *
     * @param abstractQuery
     * @return
     */
    @Override
    public T selectOne(AbstractQuery abstractQuery) {
        if (Objects.isNull(abstractQuery)) {
            return null;
        }
        return (T) dao.selectOne(abstractQuery.getAbstractWrapper());
    }

    /**
     * 根据id批量查询
     *
     * @param idList
     * @return
     */
    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return dao.selectBatchIds(idList);
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param abstractQuery 实体对象封装操作类（可以为 null）
     * @return
     */
    @Override
    @Deprecated
    public Integer selectCount(AbstractQuery abstractQuery) {
        if (Objects.isNull(abstractQuery)) {
            return 0;
        }
        return dao.selectCount(abstractQuery.getAbstractWrapper()).intValue();
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param abstractQuery 实体对象封装操作类（可以为 null）
     * @return
     */
    @Override
    public Long selectCountLong(AbstractQuery abstractQuery) {
        if (Objects.isNull(abstractQuery)) {
            return 0L;
        }
        return dao.selectCount(abstractQuery.getAbstractWrapper());
    }

    /**
     * 保存
     *
     * @param entity
     */
    @Override
    public int save(T entity) {
        return dao.insert(entity);
    }

    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize  ignore
     * @return ignore
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return SqlHelper.saveOrUpdateBatch(this.entityClass, this.mapperClass, this.log, entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            return StringUtils.checkValNull(idVal)
                    || CollectionUtils.isEmpty(sqlSession.selectList(getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        }, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(getSqlStatement(SqlMethod.UPDATE_BY_ID), param);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    /**
     * 获取mapperStatementId
     *
     * @param sqlMethod 方法名
     * @return 命名id
     * @since 3.4.0
     */
    protected String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(mapperClass, sqlMethod);
    }

    /**
     * 执行批量操作
     *
     * @param list      数据集合
     * @param batchSize 批量大小
     * @param consumer  执行方法
     * @param <E>       泛型
     * @return 操作结果
     * @since 3.3.1
     */
    protected <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.entityClass, this.log, list, batchSize, consumer);
    }

    /**
     * 更新
     *
     * @param entity
     */
    @Override
    public int updateById(T entity) {
        return dao.updateById(entity);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param abstractQuery 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return
     */
    @Override
    public int update(T entity, AbstractQuery abstractQuery) {
        return dao.update(entity, abstractQuery.getAbstractWrapper());
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    @Override
    public int deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    @Override
    public boolean deleteByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        return SqlHelper.retBool(dao.deleteByMap(columnMap));
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param abstractQuery
     */
    @Override
    public boolean delete(AbstractQuery abstractQuery) {
        return SqlHelper.retBool(dao.delete(abstractQuery.getAbstractWrapper()));
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return dao.deleteBatchIds(idList);
    }

    /**
     * 分页查询
     *
     * @param abstractQuery
     * @return
     */
    @Override
    public PageBO<T> selectPage(AbstractQuery abstractQuery) {
        if (Objects.isNull(abstractQuery)) {
            return null;
        }
        Page<T> page = dao.selectPage(abstractQuery.getPage(), abstractQuery.getAbstractWrapper());
        PageBO<T> pageBO = new PageBO<>();
        pageBO.setTotal(page.getTotal());
        pageBO.setRecords(page.getRecords());
        return pageBO;
    }
}
