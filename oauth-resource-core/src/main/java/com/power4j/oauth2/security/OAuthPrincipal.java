package com.power4j.oauth2.security;

import java.security.Principal;

/**
 * ClassName:  com.power4j.oauth2.rs.security.OAuthPrincipal <br>
 *
 * @author Kuo Hong
 * @version 2015-10-15
 */
public class OAuthPrincipal  implements Principal {


    private String name;

    public OAuthPrincipal() {
    }

    public OAuthPrincipal(String openid) {
        this.name = openid;
    }

    /**
     * 获取openID
     * @return  openID
     */
    @Override
    public String getName() {
        return name;
    }
}
