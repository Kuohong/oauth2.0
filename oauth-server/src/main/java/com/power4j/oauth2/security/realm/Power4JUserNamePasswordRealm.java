package com.power4j.oauth2.security.realm;

import com.power4j.oauth2.common.pojo.User;
import com.power4j.oauth2.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by cute on 2015/10/16.
 */
public class Power4JUserNamePasswordRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(Power4JUserNamePasswordRealm.class);
    @Resource
    private AccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String uuid = (String) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo authorizationInfo = accountService.getAccountRolePermission(uuid);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        User account = accountService.getUserByUserName(username);
        if (account == null) {
            throw new UnknownAccountException();
        }
        String accountPassword = account.getPassword();
        /*if (!password.equals(accountPassword)) {
            throw new IncorrectCredentialsException();
        }*/
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account.getUuid(), account.getPassword(), getName());
        return authenticationInfo;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
