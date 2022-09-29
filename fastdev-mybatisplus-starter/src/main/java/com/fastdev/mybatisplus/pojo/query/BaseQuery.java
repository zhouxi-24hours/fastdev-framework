package com.fastdev.mybatisplus.pojo.query;

import java.io.Serializable;

/**
 * @ClassName BaseQuery
 * @Description BaseQuery
 * @author zhouxi
 */
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1853417256402652077L;

    private String orgCode;

    private String tenantId;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
