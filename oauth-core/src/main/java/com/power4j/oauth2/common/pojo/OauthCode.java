package com.power4j.oauth2.common.pojo;


/**
 * 15-6-17
 *
 * @author Shengzhao Li
 */
public class OauthCode extends AbstractDomain {

    private String code;

    private String openid;

    private String clientId;

    public OauthCode() {
    }


    public String code() {
        return code;
    }

    public OauthCode code(String code) {
        this.code = code;
        return this;
    }

    public String getOpenid() {
        return openid;
    }

    public OauthCode openId(String openid) {
        this.openid = openid;
        return this;
    }

    public String clientId() {
        return clientId;
    }

    public OauthCode clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

}
