package com.power4j.oauth2.service.vo;

import java.io.Serializable;

/**
 * ClassName: org.power4j.oauth2.service.vo <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-14
 */
public class UserNameVo implements Serializable {

    private static final long serialVersionUID = 3453200640028685056L;
    private String clientId;

    private String username;

    public UserNameVo(String clientId, String username) {
        this.clientId = clientId;
        this.username = username;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
