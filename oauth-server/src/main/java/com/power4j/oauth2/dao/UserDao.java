package com.power4j.oauth2.dao;

import com.power4j.oauth2.common.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.Account;

/**
 * Created by cute on 2015/10/16.
 */
public interface UserDao {
    User selectUserByUserName(@Param("userName") String userName);

    User selectByUuId(@Param("uuid")String uuid);

    int saveUser(User user);

    int deleteUserRole(@Param("uuid") String uuid);
    int updateUserStatus(User user);

    int insertUserRole(@Param("userUUID")String userUUID, @Param("roleUUID")String roleUUID);
}
