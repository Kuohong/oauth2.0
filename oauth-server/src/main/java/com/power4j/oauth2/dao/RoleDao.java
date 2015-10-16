package com.power4j.oauth2.dao;

import com.power4j.oauth2.common.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cute on 2015/10/16.
 */
public interface RoleDao {
    Role selectByUUID(@Param("uuid") String uuid);

    List<Role> selectByUserUUID(@Param("uuid") String uuid);

    List<Role> selectAllEnabled();
    List<Role> selectAll();

    List<String> selectRoleUUIDByUserUUID(@Param("uuid") String uuid);

    int saveRole(Role role);
    int delete(@Param("uuid") String uuid);
}
