package org.power4j.oauth2.authorize.handler;


import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.power4j.oauth2.common.pojo.ClientDetails;
import org.power4j.oauth2.common.pojo.token.ServerAccessToken;
import org.power4j.oauth2.validator.AbstractClientDetailsValidator;
import org.power4j.oauth2.validator.TokenClientDetailsValidator;
import org.power4j.oauth2.web.util.WebUtils;
import org.power4j.oauth2.web.wapper.OAuthAuthxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handle response_type = 'token'
 *
 * @author Kuo Hong
 */
public class TokenAuthorizeHandler extends AbstractAuthorizeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizeHandler.class);


    public TokenAuthorizeHandler(OAuthAuthxRequest oauthRequest, HttpServletResponse response) {
        super(oauthRequest, response);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new TokenClientDetailsValidator(oauthRequest);
    }

    /*
    *  response token
    *
    *  If it is the first logged or first approval , always return newly BearAccessToken
    *  Always exclude refresh_token
    *
    * */
    @Override
    protected void handleResponse() throws OAuthSystemException, IOException {

        if (forceNewAccessToken()) {
            forceTokenResponse();
        } else {
            ServerAccessToken accessToken = oauthService.retrieveAccessToken(clientDetails(), oauthRequest.getScopes(), false);

            if (accessToken.tokenExpired()) {
                expiredTokenResponse(accessToken);
            } else {
                normalTokenResponse(accessToken);
            }
        }
    }

    private void forceTokenResponse() throws OAuthSystemException {
        ServerAccessToken accessToken = oauthService.retrieveNewAccessToken(clientDetails(), oauthRequest.getScopes());
        normalTokenResponse(accessToken);
    }

    private void normalTokenResponse(ServerAccessToken accessToken) throws OAuthSystemException {

        final OAuthResponse oAuthResponse = createTokenResponse(accessToken, true);
        LOG.debug("'token' response: {}", oAuthResponse);

        WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
    }

    private void expiredTokenResponse(ServerAccessToken accessToken) throws OAuthSystemException {
        final ClientDetails clientDetails = clientDetails();
        LOG.debug("BearAccessToken {} is expired", accessToken);

        final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                .setErrorDescription("access_token '" + accessToken.tokenId() + "' expired")
                .setErrorUri(clientDetails.getRedirectUri())
                .buildJSONMessage();

        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
    }

    private boolean forceNewAccessToken() {
        final ClientDetails clientDetails = clientDetails();
        if (clientDetails.isTrusted()) {
            return userFirstLogged;
        } else {
            return userFirstApproved;
        }
    }
}
