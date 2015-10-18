package com.power4j.oauth2.service.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * ClassName:  com.power4j.oauth2.rs.service.vo.OpenIDVo <br>
 *
 * @author Kuo Hong
 * @version 2015-10-14
 */
public class OpenIDVo implements Serializable {

    private static final long serialVersionUID = 3453200640028685056L;
    private String clientId;

    private String openid;

    public OpenIDVo(String clientId, String openid) {
        this.clientId = clientId;
        this.openid = openid;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OpenIDVo openIDVo = (OpenIDVo) o;
        return Objects.equals(clientId, openIDVo.clientId) && Objects
            .equals(openid, openIDVo.openid);
    }

    @Override public int hashCode() {
        return Objects.hash(clientId, openid);
    }
}
