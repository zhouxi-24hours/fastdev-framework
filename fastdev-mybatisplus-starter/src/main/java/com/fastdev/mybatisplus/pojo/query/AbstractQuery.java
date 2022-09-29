package com.fastdev.mybatisplus.pojo.query;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.baomidou.mybatisplus.core.conditions.interfaces.Join;
import com.baomidou.mybatisplus.core.conditions.interfaces.Nested;
import com.fastdev.mybatisplus.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;


/**
 * @ClassName AbstractQuery
 * @Description AbstractQuery
 * @author zhouxi
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class AbstractQuery<T, R, Children extends AbstractQuery<T, R, Children, Param>, Param> extends PageQuery<T>
        implements Compare<Children, R>, Func<Children, R>, Join<Children>, Nested<Param, Children> {

    /**
     * 子类所包装的具体 Wrapper 类型
     */
    protected Param wrapperChildren;
    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;


    @SuppressWarnings("rawtypes")
    public AbstractWrapper getAbstractWrapper() {
        return (AbstractWrapper) wrapperChildren;
    }

    /**
     * 设置分页大小
     *
     * @param pageSize
     * @return
     */
    public Children pageSize(Integer pageSize) {
        super.setPageSize(pageSize);
        return typedThis;
    }

    /**
     * 设置分页号
     *
     * @param pageNumber
     * @return
     */
    public Children pageNumber(Integer pageNumber) {
        super.setPageNumber(pageNumber);
        return typedThis;
    }

    @Override
    public <V> Children allEq(Map<R, V> params, boolean null2IsNull) {
        if (ObjectUtils.nonNull(params)) {
            getAbstractWrapper().allEq(true, params, null2IsNull);
        }
        return typedThis;
    }

    @Override
    public <V> Children allEq(boolean condition, Map<R, V> params, boolean null2IsNull) {
        if (ObjectUtils.nonNull(params)) {
            getAbstractWrapper().allEq(condition, params, null2IsNull);
        }
        return typedThis;
    }

    @Override
    public <V> Children allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
        if (ObjectUtils.nonNull(params)) {
            getAbstractWrapper().allEq(true, filter, params, null2IsNull);
        }
        return typedThis;
    }

    @Override
    public <V> Children allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
        if (ObjectUtils.nonNull(params)) {
            getAbstractWrapper().allEq(condition, filter, params, null2IsNull);
        }
        return typedThis;
    }

    @Override
    public Children eq(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().eq(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children eq(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().eq(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children ne(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().ne(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children ne(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().ne(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children gt(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().gt(column, val);
        }
        return typedThis;
    }

    @Override
    public Children gt(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().gt(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children ge(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().ge(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children ge(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().ge(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children lt(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().lt(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children lt(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().lt(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children le(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().le(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children le(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().le(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children between(R column, Object val1, Object val2) {
        if (ObjectUtils.nonNull(val1) && ObjectUtils.nonNull(val2)) {
            getAbstractWrapper().between(true, column, val1, val2);
        } else if (ObjectUtils.nonNull(val1) && Objects.isNull(val2)) {
            getAbstractWrapper().gt(true, column, val1);
        } else if (Objects.isNull(val1) && ObjectUtils.nonNull(val2)) {
            getAbstractWrapper().lt(true, column, val2);
        }
        return typedThis;
    }

    @Override
    public Children between(boolean condition, R column, Object val1, Object val2) {
        if (ObjectUtils.nonNull(val1) && ObjectUtils.nonNull(val2)) {
            getAbstractWrapper().between(condition, column, val1, val2);
        }
        return typedThis;
    }

    @Override
    public Children notBetween(R column, Object val1, Object val2) {
        if (ObjectUtils.nonNull(val1) && ObjectUtils.nonNull(val2)) {
            getAbstractWrapper().notBetween(true, column, val1, val2);
        }
        return typedThis;
    }

    @Override
    public Children notBetween(boolean condition, R column, Object val1, Object val2) {
        if (ObjectUtils.nonNull(val1) && ObjectUtils.nonNull(val2)) {
            getAbstractWrapper().notBetween(condition, column, val1, val2);
        }
        return typedThis;
    }

    @Override
    public Children like(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().like(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children like(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().like(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children notLike(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().notLike(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children notLike(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().notLike(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children likeLeft(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().likeLeft(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children likeLeft(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().likeLeft(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children likeRight(R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().likeRight(true, column, val);
        }
        return typedThis;
    }

    @Override
    public Children likeRight(boolean condition, R column, Object val) {
        if (ObjectUtils.nonNull(val)) {
            getAbstractWrapper().likeRight(condition, column, val);
        }
        return typedThis;
    }

    @Override
    public Children isNull(R column) {
        getAbstractWrapper().isNull(true, column);
        return typedThis;
    }

    @Override
    public Children isNull(boolean condition, R column) {
        getAbstractWrapper().isNull(condition, column);
        return typedThis;
    }

    @Override
    public Children isNotNull(R column) {
        getAbstractWrapper().isNotNull(true, column);
        return typedThis;
    }

    @Override
    public Children isNotNull(boolean condition, R column) {
        getAbstractWrapper().isNotNull(condition, column);
        return typedThis;
    }

    @Override
    public Children in(R column, Collection<?> coll) {
        if (!CollectionUtils.isEmpty(coll)) {
            getAbstractWrapper().in(true, column, coll);
        }
        return typedThis;
    }

    @Override
    public Children in(boolean condition, R column, Collection<?> coll) {
        if (!CollectionUtils.isEmpty(coll)) {
            getAbstractWrapper().in(condition, column, coll);
        }
        return typedThis;
    }

    /**
     * 字段 IN (v0, v1, ...)
     * <p>例: in("id", 1, 2, 3, 4, 5)</p>
     *
     * <li> 注意！数组为空若存在逻辑错误，请在 condition 条件中判断 </li>
     * <li> 如果动态数组为 empty 则不会进行 sql 拼接 </li>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    数据数组
     * @return children
     */
    @Override
    public Children in(boolean condition, R column, Object... values) {
        if (!Objects.nonNull(values)) {
            getAbstractWrapper().in(condition, column, values);
        }
        return typedThis;
    }


    @Override
    public Children notIn(R column, Collection<?> coll) {
        if (!CollectionUtils.isEmpty(coll)) {
            getAbstractWrapper().notIn(true, column, coll);
        }
        return typedThis;
    }

    @Override
    public Children notIn(boolean condition, R column, Collection<?> coll) {
        if (!CollectionUtils.isEmpty(coll)) {
            getAbstractWrapper().notIn(condition, column, coll);
        }
        return typedThis;
    }


    /**
     * 字段 NOT IN (v0, v1, ...)
     * <p>例: notIn("id", 1, 2, 3, 4, 5)</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    数据数组
     * @return children
     */
    @Override
    public Children notIn(boolean condition, R column, Object... values) {
        if (!Objects.nonNull(values)) {
            getAbstractWrapper().notIn(condition, column, values);
        }
        return typedThis;
    }

    @Override
    public Children inSql(R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().inSql(true, column, inValue);
        }
        return typedThis;
    }

    @Override
    public Children inSql(boolean condition, R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().inSql(condition, column, inValue);
        }
        return typedThis;
    }

    @Override
    public Children notInSql(R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().notInSql(true, column, inValue);
        }
        return typedThis;
    }

    @Override
    public Children notInSql(boolean condition, R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().notInSql(condition, column, inValue);
        }
        return typedThis;
    }


    /**
     * 字段 &gt; ( sql语句 )
     * <p>例1: gtSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: gtSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    @Override
    public Children gtSql(boolean condition, R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().gtSql(condition, column, inValue);
        }
        return typedThis;
    }

    /**
     * 字段 >= ( sql语句 )
     * <p>例1: geSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: geSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    @Override
    public Children geSql(boolean condition, R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().geSql(condition, column, inValue);
        }
        return typedThis;
    }

    /**
     * 字段 &lt; ( sql语句 )
     * <p>例1: ltSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: ltSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    @Override
    public Children ltSql(boolean condition, R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().ltSql(condition, column, inValue);
        }
        return typedThis;
    }

    /**
     * 字段 <= ( sql语句 )
     * <p>例1: leSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: leSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    @Override
    public Children leSql(boolean condition, R column, String inValue) {
        if (StringUtils.isNotBlank(inValue)) {
            getAbstractWrapper().leSql(condition, column, inValue);
        }
        return typedThis;
    }


    public Children groupBy(R... columns) {
        if (ObjectUtils.nonNull(columns)) {
            getAbstractWrapper().groupBy(true, columns);
        }
        return typedThis;
    }


    public Children groupBy(boolean condition, R... columns) {
        if (ObjectUtils.nonNull(columns)) {
            getAbstractWrapper().groupBy(condition, columns);
        }
        return typedThis;
    }


    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy("id")</p>
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @return children
     */
    @Override
    public Children groupBy(boolean condition, R column) {
        if (ObjectUtils.nonNull(column)) {
            getAbstractWrapper().groupBy(condition, column);
        }
        return typedThis;
    }

    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy(Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段数组
     * @return children
     */
    @Override
    public Children groupBy(boolean condition, List<R> columns) {
        if (!CollectionUtils.isEmpty(columns)) {
            getAbstractWrapper().groupBy(condition, columns);
        }
        return typedThis;
    }

    /**
     * 分组：GROUP BY 字段, ...
     *
     * @param condition
     * @param column
     * @param columns
     */
    @Override
    public Children groupBy(boolean condition, R column, R... columns) {
        if (Objects.nonNull(columns) && Objects.nonNull(column)) {
            getAbstractWrapper().groupBy(condition, column, columns);
        }
        return typedThis;
    }


    public Children orderBy(boolean isAsc, R... columns) {
        if (ObjectUtils.nonNull(columns)) {
            getAbstractWrapper().orderBy(true, isAsc, columns);
        }
        return typedThis;
    }


    public Children orderBy(boolean condition, boolean isAsc, R... columns) {
        if (ObjectUtils.nonNull(columns)) {
            getAbstractWrapper().orderBy(condition, isAsc, columns);
        }
        return typedThis;
    }


    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, "id")</p>
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param column    单个字段
     * @return children
     */
    @Override
    public Children orderBy(boolean condition, boolean isAsc, R column) {
        if (ObjectUtils.nonNull(column)) {
            getAbstractWrapper().orderBy(condition, isAsc, column);
        }
        return typedThis;
    }

    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param columns   字段列表
     * @return children
     */
    @Override
    public Children orderBy(boolean condition, boolean isAsc, List<R> columns) {
        if (!CollectionUtils.isEmpty(columns)) {
            getAbstractWrapper().orderBy(condition, isAsc, columns);
        }
        return typedThis;
    }

    /**
     * 排序：ORDER BY 字段, ...
     *
     * @param condition
     * @param isAsc
     * @param column
     * @param columns
     */
    @Override
    public Children orderBy(boolean condition, boolean isAsc, R column, R... columns) {
        if (ObjectUtils.nonNull(column) && ObjectUtils.nonNull(columns)) {
            getAbstractWrapper().orderBy(condition, isAsc, column, columns);
        }
        return typedThis;
    }

    @Override
    public Children having(String sqlHaving, Object... params) {
        if (ObjectUtils.nonNull(params)) {
            getAbstractWrapper().having(true, sqlHaving, params);
        }
        return typedThis;
    }

    @Override
    public Children having(boolean condition, String sqlHaving, Object... params) {
        if (ObjectUtils.nonNull(params)) {
            getAbstractWrapper().having(condition, sqlHaving, params);
        }
        return typedThis;
    }

    @Override
    public Children func(boolean condition, Consumer<Children> consumer) {
        if (condition) {
            consumer.accept(typedThis);
        }
        return typedThis;
    }

    @Override
    public Children or(boolean condition) {
        getAbstractWrapper().or(condition);
        return typedThis;
    }

    @Override
    public Children apply(boolean condition, String applySql, Object... value) {
        if (ObjectUtils.nonNull(value)) {
            getAbstractWrapper().apply(condition, applySql, value);
        }
        return typedThis;
    }

    @Override
    public Children last(boolean condition, String lastSql) {
        if (StringUtils.isNotBlank(lastSql)) {
            getAbstractWrapper().last(condition, lastSql);
        }
        return typedThis;
    }

    @Override
    public Children comment(boolean condition, String comment) {
        if (StringUtils.isNotBlank(comment)) {
            getAbstractWrapper().comment(condition, comment);
        }
        return typedThis;
    }

    @Override
    public Children first(boolean condition, String firstSql) {
        if (StringUtils.isNotBlank(firstSql)) {
            getAbstractWrapper().first(condition, firstSql);
        }
        return typedThis;
    }


    public Children exists(boolean condition, String existsSql) {
        if (StringUtils.isNotBlank(existsSql)) {
            getAbstractWrapper().exists(condition, existsSql);
        }
        return typedThis;
    }


    public Children notExists(boolean condition, String existsSql) {
        if (StringUtils.isNotBlank(existsSql)) {
            getAbstractWrapper().notExists(condition, existsSql);
        }
        return typedThis;
    }

    /**
     * 拼接 EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: exists("select id from table where age = 1")</p>
     *
     * @param condition 执行条件
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    @Override
    public Children exists(boolean condition, String existsSql, Object... values) {
        if (StringUtils.isNotBlank(existsSql) && Objects.nonNull(values)) {
            getAbstractWrapper().exists(condition, existsSql, values);
        }
        return typedThis;
    }

    /**
     * 拼接 NOT EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: notExists("select id from table where age = 1")</p>
     *
     * @param condition 执行条件
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    @Override
    public Children notExists(boolean condition, String existsSql, Object... values) {
        if (StringUtils.isNotBlank(existsSql) && Objects.nonNull(values)) {
            getAbstractWrapper().notExists(condition, existsSql, values);
        }
        return typedThis;
    }

    @Override
    public Children and(Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().and(true, consumer);
        }
        return typedThis;
    }

    @Override
    public Children and(boolean condition, Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().and(condition, consumer);
        }
        return typedThis;
    }


    @Override
    public Children or(Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().or(true, consumer);
        }
        return typedThis;
    }

    @Override
    public Children or(boolean condition, Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().or(condition, consumer);
        }
        return typedThis;
    }

    @Override
    public Children nested(Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().nested(true, consumer);
        }
        return typedThis;
    }


    @Override
    public Children nested(boolean condition, Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().nested(condition, consumer);
        }
        return typedThis;
    }

    @Override
    public Children not(Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().not(true, consumer);
        }
        return typedThis;
    }

    @Override
    public Children not(boolean condition, Consumer<Param> consumer) {
        if (ObjectUtils.nonNull(consumer)) {
            getAbstractWrapper().not(condition, consumer);
        }
        return typedThis;
    }


    public void clear() {
        getAbstractWrapper().clear();
    }
}
