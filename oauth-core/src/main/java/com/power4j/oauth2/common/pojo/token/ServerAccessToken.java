package com.power4j.oauth2.common.pojo.token;


import com.power4j.oauth2.common.constants.OauthConstants;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.util.OauthUtils;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;

import java.util.Date;

/**
 * ClassName: com.monkeyk.os.domain.oauth <br>
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public  class ServerAccessToken extends AccessToken {



    protected static long THOUSAND = 1000l;


    protected String openid;

    protected String clientId;

    protected String authenticationId;


    protected int tokenExpiredSeconds = OauthConstants.ACCESS_TOKEN_VALIDITY_SECONDS;

    protected int refreshTokenExpiredSeconds = OauthConstants.REFRESH_TOKEN_VALIDITY_SECONDS;
    protected Date createTime;


    public ServerAccessToken() {
        super();
    }

    protected ServerAccessToken(ClientDetails client, String tokenType, String tokenKey,
        long expiresIn) {
        this(client, tokenType, tokenKey, expiresIn, OauthUtils.getIssuedAt());
    }

    protected ServerAccessToken(ClientDetails client, String tokenType, String tokenKey,
        long expiresIn, long issuedAt) {
        super(tokenType, tokenKey, expiresIn, issuedAt);
        this.clientId = client.getClientId();
    }

    protected ServerAccessToken(ServerAccessToken token, String key) {
        super(token.getTokenType(), key, token.getTokenExpiredSeconds(), token.getIssuedAt(),
            token.getRefreshToken(), token.getParameters());
        this.clientId = token.getClientId();
        //this.scopes = token.getScopes();
        //this.audience = token.getAudience();
        //this.subject = token.getSubject();
    }


    public boolean isTokenExpired() {
        final long time = this.getIssuedAt();
        return (System.currentTimeMillis() / THOUSAND - time) > OauthConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
    }


    public boolean refreshTokenExpired() {
        final long time = this.getIssuedAt();
        return (System.currentTimeMillis() / THOUSAND - time) > OauthConstants.REFRESH_TOKEN_VALIDITY_SECONDS;
    }


    public long currentTokenExpiredSeconds() {
        if (isTokenExpired()) {
            return -1;
        }
        final long time = this.getIssuedAt();
        return OauthConstants.ACCESS_TOKEN_VALIDITY_SECONDS - (System.currentTimeMillis()/THOUSAND -time);
    }

    public ServerAccessToken updateByClientDetails(ClientDetails clientDetails) {
        this.clientId = clientDetails.getClientId();

        final Integer accessTokenValidity = clientDetails.getAccessTokenValidity();
        if (accessTokenValidity != null && accessTokenValidity > 0) {
            this.tokenExpiredSeconds = accessTokenValidity;
        }

        final Integer refreshTokenValidity = clientDetails.getRefreshTokenValidity();
        if (refreshTokenValidity != null && refreshTokenValidity > 0) {
            this.refreshTokenExpiredSeconds = refreshTokenValidity;
        }

        return this;
    }


    public ServerAccessToken tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public String getOpenid() {
        return openid;
    }

    public ServerAccessToken openId(String openid) {
        this.openid = openid;
        return this;
    }

    public String clientId() {
        return clientId;
    }

    public ServerAccessToken clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String refreshToken() {
        return refreshToken;
    }

    public ServerAccessToken refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String tokenType() {
        return tokenType;
    }

    public ServerAccessToken tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public int tokenExpiredSeconds() {
        return tokenExpiredSeconds;
    }

    public ServerAccessToken tokenExpiredSeconds(int tokenExpiredSeconds) {
        this.tokenExpiredSeconds = tokenExpiredSeconds;
        return this;
    }

    public int refreshTokenExpiredSeconds() {
        return refreshTokenExpiredSeconds;
    }

    public ServerAccessToken refreshTokenExpiredSeconds(int refreshTokenExpiredSeconds) {
        this.refreshTokenExpiredSeconds = refreshTokenExpiredSeconds;
        return this;
    }


    public String authenticationId() {
        return authenticationId;
    }

    public ServerAccessToken authenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
        return this;
    }

    @Override public String toString() {
        return "{" +
            "tokenId='" + tokenId + '\'' +
            ", openid='" + openid + '\'' +
            ", clientId='" + clientId + '\'' +
            ", authenticationId='" + authenticationId + '\'' +
            ", refreshToken='" + refreshToken + '\'' +
            ", tokenType='" + tokenType + '\'' +
            ", tokenExpiredSeconds=" + tokenExpiredSeconds +
            ", refreshTokenExpiredSeconds=" + refreshTokenExpiredSeconds +
            '}';
    }



    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    @Override public long getTokenExpiredSeconds() {
        return tokenExpiredSeconds;
    }

    public void setTokenExpiredSeconds(int tokenExpiredSeconds) {
        this.tokenExpiredSeconds = tokenExpiredSeconds;
    }

    public int getRefreshTokenExpiredSeconds() {
        return refreshTokenExpiredSeconds;
    }

    public void setRefreshTokenExpiredSeconds(int refreshTokenExpiredSeconds) {
        this.refreshTokenExpiredSeconds = refreshTokenExpiredSeconds;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.issuedAt = this.getCreateTime().getTime()/OauthConstants.THOUSAND;
    }

    protected static ServerAccessToken validateTokenType(ServerAccessToken token,
        String expectedType) {
        if (!token.getTokenType().equals(expectedType)) {
            throw new OAuthRuntimeException(OauthConstants.SERVER_ERROR);
        }
        return token;
    }

    @Override public long getIssuedAt() {
        return this.getCreateTime().getTime()/OauthConstants.THOUSAND;
    }



    /**
     * Clone
     * Exclude token, refresh_token, authenticationId, expired
     *
     * @return New ServerAccessToken instance
     */
    public  ServerAccessToken cloneMe(){
        ServerAccessToken serverAccessToken = new ServerAccessToken();
        serverAccessToken.setTokenType(tokenType);
        serverAccessToken.setAuthenticationId(authenticationId);
        serverAccessToken.clientId(clientId);
        serverAccessToken.setRefreshToken(refreshToken);
        serverAccessToken.setRefreshTokenExpiredSeconds(refreshTokenExpiredSeconds);
        serverAccessToken.setTokenId(tokenId);
        serverAccessToken.setTokenExpiredSeconds(tokenExpiredSeconds);
        serverAccessToken.setOpenid(openid);
        serverAccessToken.setCreateTime(createTime);
        return serverAccessToken;
    }
}
