package com.power4j.oauth2.provider;

import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.common.pojo.token.refresh.RefreshToken;
import com.power4j.oauth2.exception.OAuthServiceException;
import com.power4j.oauth2.service.OauthService;

import java.util.List;

/**
 * ClassName: com.power4j.oauth2.provider.DefaultDbOAuthDataProvider <br>
 *
 * @author Kuo Hong
 * @version 2015-10-20
 */
public class DefaultDbOAuthDataProvider  extends AbstractOAuthDataProvider implements ClientRegistrationProvider{
    private OauthService oauthService;

    public OauthService getOauthService() {
        return oauthService;
    }

    public void setOauthService(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @Override protected void saveAccessToken(ServerAccessToken serverToken) {
        //oauthService.
    }

    @Override protected void saveRefreshToken(ServerAccessToken at, RefreshToken refreshToken) {

    }

    @Override protected boolean revokeAccessToken(String accessTokenKey) {
        return false;
    }

    @Override
    protected RefreshToken revokeRefreshToken(ClientDetails client, String refreshTokenKey) {
        return null;
    }

    @Override public void setClient(ClientDetails client) {

    }

    @Override public ClientDetails removeClient(String clientId) {
        return null;
    }

    @Override public List<ClientDetails> getClients() {
        return null;
    }

    /**
     * Returns the previously registered third-party {@link ClientDetails}
     *
     * @param clientId the client id
     * @return Client
     */
    @Override public ClientDetails getClient(String clientId) {
        return null;
    }

    /**
     * Refresh access token
     *
     * @param client          the client
     * @param refreshToken    refresh token key
     * @param requestedScopes the scopes requested by the client
     * @return AccessToken
     */
    @Override public ServerAccessToken refreshAccessToken(ClientDetails client, String refreshToken,
        List<String> requestedScopes) throws OAuthServiceException {
        return null;
    }

    /**
     * Removes the access token
     * The runtime will call this method if it finds that a token has expired
     *
     * @param serverAccessToken the token
     */
    @Override public void removeAccessToken(ServerAccessToken serverAccessToken)
        throws OAuthServiceException {
        revokeAccessToken(serverAccessToken.getTokenId());
    }
}
