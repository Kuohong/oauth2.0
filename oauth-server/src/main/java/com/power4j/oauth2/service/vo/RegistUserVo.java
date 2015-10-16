package com.power4j.oauth2.service.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by cute on 2015/10/16.
 */
public class RegistUserVo {
    @NotNull(message = "账号格式错误")
    private String account;
    @NotNull(message = "密码格式错误")
    private String  password;



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
