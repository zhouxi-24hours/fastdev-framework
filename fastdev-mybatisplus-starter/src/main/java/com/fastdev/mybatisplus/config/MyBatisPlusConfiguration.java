package com.fastdev.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName MyBatisPlusConfiguration
 * @Description MyBatisPlusConfiguration
 * @author zhouxi
 */
public class MyBatisPlusConfiguration {

    private final DbProperties dbProperties;

    public MyBatisPlusConfiguration(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor paginationInterceptor = new MybatisPlusInterceptor();
        paginationInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(dbProperties.getDbType()));
        paginationInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return paginationInterceptor;
    }
}
