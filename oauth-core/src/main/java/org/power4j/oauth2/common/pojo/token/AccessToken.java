package org.power4j.oauth2.common.pojo.token;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ClassName: com.monkeyk.os.domain.oauth <br>
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public class AccessToken implements Serializable {
    protected String tokenId;
    protected String tokenType;
    protected String refreshToken;
    protected long tokenExpiredSeconds = -1;
    private long issuedAt = -1;
    private Map<String, String> parameters = new LinkedHashMap<String, String>();

    public AccessToken() {

    }

    public AccessToken(String tokenType, String tokenKey) {
        this.tokenType = tokenType;
        this.tokenId = tokenKey;
    }

    public AccessToken(String tokenType, String tokenKey, long expiresIn, long issuedAt) {
        this(tokenType, tokenKey);
        this.tokenExpiredSeconds = expiresIn;
        this.issuedAt = issuedAt;
    }

    public AccessToken(String tokenType, String tokenKey, long expiresIn, long issuedAt,
        String refreshToken, Map<String, String> parameters) {
        this(tokenType, tokenKey, expiresIn, issuedAt);
        this.refreshToken = refreshToken;
        this.parameters = parameters;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getTokenExpiredSeconds() {
        return tokenExpiredSeconds;
    }

    public void setTokenExpiredSeconds(long tokenExpiredSeconds) {
        this.tokenExpiredSeconds = tokenExpiredSeconds;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
