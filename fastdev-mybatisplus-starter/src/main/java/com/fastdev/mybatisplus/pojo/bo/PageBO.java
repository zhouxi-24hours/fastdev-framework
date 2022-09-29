package com.fastdev.mybatisplus.pojo.bo;


import cn.hutool.json.JSONUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageBO
 * @Description PageBO
 * @Author Fly
 * @Version
 * @Date 2021/1/28 9:50
 */
public class PageBO<T> implements Serializable {
    private static final long serialVersionUID = 5205351084869387926L;

    private Long total;

    private List<T> records;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
