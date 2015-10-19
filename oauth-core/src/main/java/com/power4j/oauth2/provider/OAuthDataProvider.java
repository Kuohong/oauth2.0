package com.power4j.oauth2.provider;

import com.power4j.oauth2.common.AccessTokenRegistration;
import com.power4j.oauth2.common.constants.TokenType;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.exception.OAuthServiceException;

import java.util.List;

/**
 * ClassName: com.power4j.oauth2.provider.OAuthDataProvider <br>
 *
 * OAuth provider responsible for persisting the information about
 * OAuth consumers, request and access tokens.
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public interface OAuthDataProvider {


    /**
     * Returns the previously registered third-party {@link ClientDetails}
     * @param clientId the client id
     * @return Client
     */
    ClientDetails getClient(String clientId) throws OAuthServiceException;

    /**
     * Create access token
     * @param accessTokenRegistration the token registration info
     * @return AccessToken
     */
    ServerAccessToken createAccessToken(AccessTokenRegistration accessTokenRegistration) throws OAuthServiceException ;

    /**
     * Refresh access token
     * @param client the client
     * @param refreshToken refresh token key
     * @param requestedScopes the scopes requested by the client
     * @return AccessToken
     */
    ServerAccessToken refreshAccessToken(ClientDetails client,
        String refreshToken,
        List<String> requestedScopes) throws OAuthServiceException;

    /**
     * Removes the access token
     * The runtime will call this method if it finds that a token has expired
     * @param tokenId the token
     */
    void removeAccessToken(ServerAccessToken tokenId) throws OAuthServiceException ;

    /**
     * Revokes a refresh or access token
     * @param token token identifier
     * @param tokenTypeHint
     */
    void revokeToken(ClientDetails client, String token, String tokenTypeHint) throws OAuthServiceException;
}
