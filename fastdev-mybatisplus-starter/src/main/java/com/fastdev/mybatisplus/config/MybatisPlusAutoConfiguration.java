package com.fastdev.mybatisplus.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * @author zhouxi
 * @className MybatisPlusAutoConfiguration
 * @date 2022/9/29 8:49
 **/

@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties({MybatisPlusProperties.class, DbProperties.class})
@Import({MyBatisPlusConfiguration.class})
public class MybatisPlusAutoConfiguration {

    private final DbProperties dbProperties;

    public MybatisPlusAutoConfiguration(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MyMetaObjectHandler.class)
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }
}
