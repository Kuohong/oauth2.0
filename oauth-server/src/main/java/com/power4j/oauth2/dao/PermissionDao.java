package com.power4j.oauth2.dao;

import com.power4j.oauth2.common.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by cute on 2015/10/16.
 */
public interface PermissionDao {
    List<Permission> selectAll();

    List<Permission> selectPermissionByRoleUUID(@Param("roleUUID")String uuid);
    Set<String> selectPermissionKeyByRoleUUID(@Param("roleUUID") String uuid);
    int savePermission(Permission permission);
}
