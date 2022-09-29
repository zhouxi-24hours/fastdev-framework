package com.fastdev.mybatisplus.pojo.query;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName PageQuery
 * @Description PageQuery
 * @author zhouxi
 */
public class PageQuery<T> implements Serializable {

    /**
     * 分页大小
     */
    @NotNull(message = "每页的数量不能为空")
    private Integer pageSize;

    /**
     * 当前页码
     */
    @NotNull(message = "当前页码不能为空")
    private Integer pageNumber;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Page<T> getPage() {
        if (this.pageNumber != null && this.pageSize != null) {
            return new Page<>(this.pageNumber, this.pageSize);
        }
        return new Page<>();
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
