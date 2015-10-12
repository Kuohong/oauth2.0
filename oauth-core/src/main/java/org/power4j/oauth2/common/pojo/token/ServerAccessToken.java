package org.power4j.oauth2.common.pojo.token;


import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.power4j.oauth2.common.constants.OauthConstants;
import org.power4j.oauth2.common.pojo.ClientDetails;
import org.power4j.oauth2.common.util.OauthUtils;

/**
 * ClassName: com.monkeyk.os.domain.oauth <br>
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public abstract class ServerAccessToken extends AccessToken {
    //Default value
    public static int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30; // default 30 days.
    //Default value
    public static int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12; // default 12 hours.


    protected static long THOUSAND = 1000l;


    protected String username;

    protected String clientId;

    protected String authenticationId;


    protected int tokenExpiredSeconds = ACCESS_TOKEN_VALIDITY_SECONDS;

    protected int refreshTokenExpiredSeconds = REFRESH_TOKEN_VALIDITY_SECONDS;


    public ServerAccessToken() {
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
        return time < System.currentTimeMillis() / THOUSAND;
    }


    public boolean refreshTokenExpired() {
        final long time = this.getIssuedAt();
        return time < System.currentTimeMillis() / THOUSAND;
    }


    public long currentTokenExpiredSeconds() {
        if (isTokenExpired()) {
            return -1;
        }
        final long time = this.getIssuedAt();
        return time - System.currentTimeMillis() / THOUSAND;
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

    public String tokenId() {
        return tokenId;
    }

    public ServerAccessToken tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public String username() {
        return username;
    }

    public ServerAccessToken username(String username) {
        this.username = username;
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
            ", username='" + username + '\'' +
            ", clientId='" + clientId + '\'' +
            ", authenticationId='" + authenticationId + '\'' +
            ", refreshToken='" + refreshToken + '\'' +
            ", tokenType='" + tokenType + '\'' +
            ", tokenExpiredSeconds=" + tokenExpiredSeconds +
            ", refreshTokenExpiredSeconds=" + refreshTokenExpiredSeconds +
            '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    protected static ServerAccessToken validateTokenType(ServerAccessToken token,
        String expectedType) {
        if (!token.getTokenType().equals(expectedType)) {
            throw new OAuthRuntimeException(OauthConstants.SERVER_ERROR);
        }
        return token;
    }



    /**
     * Clone
     * Exclude token, refresh_token, authenticationId, expired
     *
     * @return New ServerAccessToken instance
     */
    public abstract ServerAccessToken cloneMe();
}
