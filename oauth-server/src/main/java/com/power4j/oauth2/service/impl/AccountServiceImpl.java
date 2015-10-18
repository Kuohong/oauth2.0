package com.power4j.oauth2.service.impl;

import com.power4j.oauth2.common.pojo.CommonStatus;
import com.power4j.oauth2.common.pojo.Permission;
import com.power4j.oauth2.common.pojo.Role;
import com.power4j.oauth2.common.pojo.User;
import com.power4j.oauth2.dao.PermissionDao;
import com.power4j.oauth2.dao.RoleDao;
import com.power4j.oauth2.dao.UserDao;
import com.power4j.oauth2.service.AccountService;
import com.power4j.oauth2.service.vo.RegistUserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by cute on 2015/10/16.
 */
@Service("AccountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private UserDao userDao;

    /**
     * 根据账号uuid获取授权
     *
     * @param accountUUID
     * @return
     */

    @Override public SimpleAuthorizationInfo getAccountRolePermission(String accountUUID) {
        SimpleAuthorizationInfo token = new SimpleAuthorizationInfo();
        /*//获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();*/
        //用户角色名字
        Set<String> roleNameSet = new HashSet<String>();
        //权限名字
        Set<String> perNameSet = new HashSet<String>();
        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        //获取用户角色id
        List<Role> roleList = roleDao.selectByUserUUID(accountUUID);
        for (Role role : roleList){
            roleNameSet.add(role.getKey());
            Set<String> permissions = permissionDao.selectPermissionKeyByRoleUUID(role.getUuid());
            token.setStringPermissions(permissions);
        }

        //设置角色权限
        token.setRoles(roleNameSet);/*
        token.setStringPermissions(perNameSet);*/
        return token;

    }
    /**
     * 根据账号获取账号对象
     *
     * @param account
     * @return
     */

    @Override public User getUserByUserName(String account) {
        return userDao.selectUserByUserName(account);
    }

    @Override public void saveUser(RegistUserVo userVo) {
        User dbUser = userDao.selectUserByUserName(userVo.getAccount());
        if(dbUser == null ){
            //TODO EXCEPTION?
            throw new RuntimeException("");
        }
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setUserName(userVo.getAccount());
        user.setPassword(userVo.getPassword());
        user.setCreateTime(new Date());
        userDao.saveUser(user);
    }

    @Override public void deleteUser(String uuid) {
        User user = userDao.selectByUuId(uuid);
        user.setStatus(CommonStatus.DISABLE);
        userDao.updateUserStatus(user);
    }

    @Override public void updateUserRole(String uuid, String roleUUDs) {
        userDao.deleteUserRole(uuid);

        if (StringUtils.isEmpty(roleUUDs)) return;
        // 建立新关系
        String[] ids = roleUUDs.split(",");
        for (String roleId : ids) {
            userDao.insertUserRole(uuid, roleId);
        }
    }
}
