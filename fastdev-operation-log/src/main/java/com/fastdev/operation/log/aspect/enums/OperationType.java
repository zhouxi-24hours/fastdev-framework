package com.fastdev.operation.log.aspect.enums;

/**
 * @author zhouxi
 * @className OperationType
 * @date 2022/9/29 9:54
 **/
public enum OperationType {

    /**
     * 新增、修改、删除
     *
     */

    ADD,
    UPDATE,
    DELETE;

    public String getType() {
        if (this.equals(ADD)) {
            return "ADD";
        }
        if (this.equals(UPDATE)) {
            return "UPDATE";
        }
        if (this.equals(DELETE)) {
            return "DELETE";
        }
        return null;
    }
}
