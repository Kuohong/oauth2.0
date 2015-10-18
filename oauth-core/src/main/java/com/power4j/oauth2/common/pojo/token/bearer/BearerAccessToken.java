package com.power4j.oauth2.common.pojo.token.bearer;


import com.power4j.oauth2.common.constants.OauthConstants;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;

/**
 * BearerAccessToken
 *
 * @author Kuo Hong
 */
public class BearerAccessToken extends ServerAccessToken {
    public BearerAccessToken() {
        tokenType = OauthConstants.BEARER_TOKEN_TYPE;
    }


    public BearerAccessToken(ClientDetails client, String tokenKey, long lifetime, long issuedAt) {
        super(client, OauthConstants.BEARER_TOKEN_TYPE, tokenKey, lifetime, issuedAt);
    }

    public BearerAccessToken(ServerAccessToken token, String newKey) {
        super(validateTokenType(token, OauthConstants.BEARER_TOKEN_TYPE), newKey);
    }

    /**
     * Clone
     * Exclude token, refresh_token, authenticationId, expired
     *
     * @return New ServerAccessToken instance
     */
    public ServerAccessToken cloneMe() {
        BearerAccessToken token = new BearerAccessToken();
        token.setOpenid(openid);
        token.setClientId(clientId);
        token.setCreateTime(createTime);
        token.setRefreshToken(refreshToken);
        token.setTokenType(tokenType);
        return token;
    }
}
