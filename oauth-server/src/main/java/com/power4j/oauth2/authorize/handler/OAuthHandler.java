package com.power4j.oauth2.authorize.handler;


import com.power4j.framework.context.util.BeanProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kuo Hong
 */
public abstract class OAuthHandler {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthHandler.class);

    protected transient OauthService oauthService = BeanProvider.getBean(OauthService.class);


    private ClientDetails clientDetails;


    protected ClientDetails clientDetails() {
        if (clientDetails == null) {
            final String clientId = clientId();
            clientDetails = oauthService.loadClientDetails(clientId);
            LOG.debug("Load ClientDetails: {} by clientId: {}", clientDetails, clientId);
        }
        return clientDetails;
    }


    /**
     * Create  BearAccessToken response
     *
     * @param accessToken BearAccessToken
     * @param queryOrJson True is QueryMessage, false is JSON message
     * @return OAuthResponse
     * @throws OAuthSystemException
     */
    protected OAuthResponse createTokenResponse(ServerAccessToken accessToken, boolean queryOrJson) throws OAuthSystemException {
        final ClientDetails clientDetails = clientDetails();

        final OAuthASResponse.OAuthTokenResponseBuilder builder = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .location(clientDetails.getRedirectUri())
                .setAccessToken(accessToken.getTokenId())
                .setExpiresIn(String.valueOf(accessToken.currentTokenExpiredSeconds()))
                .setTokenType(accessToken.tokenType());

        final String refreshToken = accessToken.refreshToken();
        if (StringUtils.isNotEmpty(refreshToken)) {
            builder.setRefreshToken(refreshToken);
        }

        return queryOrJson ? builder.buildQueryMessage() : builder.buildJSONMessage();
    }


    protected abstract String clientId();

    public OauthService getOauthService() {
        return oauthService;
    }

    public void setOauthService(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }
}
