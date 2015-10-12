package org.power4j.oauth2.common.pojo.token.bearer;


import org.power4j.oauth2.common.constants.OauthConstants;
import org.power4j.oauth2.common.pojo.ClientDetails;
import org.power4j.oauth2.common.pojo.token.ServerAccessToken;

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
        return new BearerAccessToken().username(username).clientId(clientId).tokenType(tokenType);
    }
}
