package com.power4j.oauth2.rs.service.vo;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * Created by cute on 2015/10/13.
 */
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 2583239228113727460L;
    private String username;
    private String password;

    public LoginVo() {
    }

    public UsernamePasswordToken token() {
        return new UsernamePasswordToken(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
