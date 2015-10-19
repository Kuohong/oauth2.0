package com.power4j.oauth2.common.pojo.token.refresh;

import com.power4j.oauth2.common.constants.OAuthConstants;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.util.OAuthUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * ClassName: com.power4j.oauth2.common.pojo.token.refresh.RefreshToken <br>
 * Simple Refresh Token implementation
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public class RefreshToken extends ServerAccessToken {

    private static final long serialVersionUID = 2837120382251693874L;
    private List<String> accessTokens = new LinkedList<String>();

    public RefreshToken(ClientDetails client, String tokenId,
        long lifetime) {
        super(client,
            OAuthConstants.REFRESH_TOKEN_TYPE,
            tokenId,
            lifetime,
            OAuthUtils.getIssuedAt());
    }


    public RefreshToken(ServerAccessToken token,
        String key,
        List<String> accessTokens) {
        super(validateTokenType(token, OAuthConstants.REFRESH_TOKEN_TYPE), key);
        this.accessTokens = accessTokens;
    }
    public RefreshToken() {

    }

    public List<String> getAccessTokens() {
        return accessTokens;
    }

    public void setAccessTokens(List<String> accessTokens) {
        this.accessTokens = accessTokens;
    }

    public void addAccessToken(String token) {
        accessTokens.add(token);
    }

    public boolean removeAccessToken(String token) {
        return accessTokens.remove(token);
    }
}
