package com.fastdev.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Fly
 * @Version
 * @Date 2021/1/28 10:40
 */
@ConfigurationProperties(prefix = "fastdev.db")
public class DbProperties {

    /**
     * 默认秘钥
     */
    private final String DEFAULT_KEY = "fastdev@123456";
    /**
     * 数据库类型
     */
    private DbType dbType = DbType.MYSQL;

    /**
     * 敏感字段加密配置
     */
    private String desPrivateKey = DEFAULT_KEY;

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public String getDesPrivateKey() {
        return desPrivateKey;
    }

    public void setDesPrivateKey(String desPrivateKey) {
        this.desPrivateKey = desPrivateKey;
    }
}
