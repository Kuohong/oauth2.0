package org.power4j.oauth2.common.pojo;

import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.io.Serializable;

/**
 * ClassName: org.power4j.common.pojo <br>
 *
 * @author Kuo Hong
 * @version 2015-10-11
 */
public class ClientDetails implements Serializable {
    /**
     * 名称
     */
    protected String name;
    /**
     * clientId
     */
    protected String clientId;
    /**
     * clientSecret
     */
    protected String clientSecret;
    /**
     * redirectUri
     */
    protected String redirectUri;
    protected String clientUri;
    /**
     * 描述
     */
    protected String description;
    /**
     * 图标
     */
    protected String iconUri;
    protected Long issuedAt;
    protected Long expiresIn;
    protected String resourceIds;

    protected String scope;

    protected String grantTypes;

    /*
   * Shiro roles
   * */
    protected String roles;

    protected Integer accessTokenValidity;

    protected Integer refreshTokenValidity;
    /**
     * 是否是受信任的
     */
    protected boolean trusted = false;
    /**
     * 状态
     */
    protected ClientStatus status = ClientStatus.SUBMITTED;
    /**
     * 是否是机密的
     */
    protected boolean isConfidential;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public boolean isConfidential() {
        return isConfidential;
    }

    public void setIsConfidential(boolean isConfidential) {
        this.isConfidential = isConfidential;
    }
    public boolean isSupportRefreshToken() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.REFRESH_TOKEN.toString());
    }
}
