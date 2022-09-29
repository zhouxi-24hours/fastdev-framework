package com.fastdev.mybatisplus.util;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastdev.mybatisplus.pojo.bo.PageBO;
import com.fastdev.mybatisplus.pojo.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ConvertUtils
 * @Description ConvertUtils
 * @author zhouxi
 */
public class ConvertUtils {

    public static <T> PageVO<T> pageBOtoPageVO(PageBO pageBO, Class<T> clazz) {
        List<T> records = pageBO.getRecords();
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(BeanUtils.convertList(records, clazz));
        pageVO.setTotal(pageBO.getTotal());
        return pageVO;
    }

    public static <T> PageBO<T> pagePOtoPageBO(PageBO pageBO, Class<T> clazz) {
        List poList = pageBO.getRecords();
        List<T> boList = BeanUtils.convertList(poList, clazz);
        PageBO<T> boPageBO = new PageBO<>();
        boPageBO.setTotal(pageBO.getTotal());
        boPageBO.setRecords(boList);
        return boPageBO;
    }

    public static <D> PageBO<D> pageMapToPageBO(Page<Map<String, Object>> mapPage, Class<D> clazz) {
        List<Map<String, Object>> mapList = mapPage.getRecords();
        List<D> doList = JSONUtil.toList(JSONUtil.toJsonStr(mapList), clazz);
        PageBO<D> boPageBO = new PageBO<>();
        boPageBO.setTotal(mapPage.getTotal());
        boPageBO.setRecords(doList);
        return boPageBO;
    }
}
