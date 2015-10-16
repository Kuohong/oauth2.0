package com.power4j.oauth2.service;

import com.power4j.oauth2.common.pojo.User;
import com.power4j.oauth2.service.vo.RegistUserVo;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

/**
 * Created by cute on 2015/10/16.
 */
public interface AccountService {
    /**
     * 根据账号uuid获取授权
     *
     * @param accountUUID
     * @return
     */
    SimpleAuthorizationInfo getAccountRolePermission(String accountUUID);

    /**
     * 根据账号获取账号对象
     *
     * @param account
     * @return
     */
    User getUserByUserName(String account);

    void saveUser(RegistUserVo userVo);

    void deleteUser(String uuid);

    void updateUserRole(String uuid, String roleUUDs);

}
