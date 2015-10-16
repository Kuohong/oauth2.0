package com.power4j.oauth2.common.pojo;

import java.util.Date;

/**
 * Created by cute on 2015/10/16.
 */
public class Permission {
    /**
     * permission.id (权限id)
     * @ibatorgenerated 2015-09-01 10:10:36
     */
    private Integer id;
    /**
     * UUID
     */
    private String uuid;

    /**
     * permission.name (权限名字)
     * @ibatorgenerated 2015-09-01 10:10:36
     */
    private String name;

    /**
     * permission.key (权限key)
     * @ibatorgenerated 2015-09-01 10:10:36
     */
    private String key;

    /**
     * permission.parent_id (上级权限)
     * @ibatorgenerated 2015-09-01 10:10:36
     */
    private String parentUUID;

    /**
     * permission.order (权限排序)
     * @ibatorgenerated 2015-09-01 10:10:36
     */
    private Integer order;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentUUID() {
        return parentUUID;
    }

    public void setParentUUID(String parentUUID) {
        this.parentUUID = parentUUID;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
