package com.power4j.oauth2.web.controller;


import com.power4j.framework.context.util.BeanProvider;
import com.power4j.oauth2.common.web.util.WebUtils;
import com.power4j.oauth2.provider.HandlerProvider;
import com.power4j.oauth2.token.handler.AbstractOAuthTokenHandler;
import com.power4j.oauth2.token.handler.OAuthTokenHandler;
import com.power4j.oauth2.web.wapper.OAuthAuthxRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import com.power4j.oauth2.token.handler.OAuthTokenHandleDispatcher;
import com.power4j.oauth2.web.wapper.OAuthTokenxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * URL: oauth2.0/token
 *
 * @author Kuo Hong
 */
@Controller
@RequestMapping("oauth2.0/")
public class OauthTokenController {

    private static final Logger LOG = LoggerFactory.getLogger(OauthTokenController.class);
    private HandlerProvider handlerProvider = BeanProvider.getBean(HandlerProvider.class);

    /**
     * Handle grant_types as follows:
     * <p/>
     * grant_type=authorization_code
     * grant_type=password
     * grant_type=refresh_token
     * grant_type=client_credentials
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping("token")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            OAuthTokenxRequest tokenRequest = new OAuthTokenxRequest(request);

           /* OAuthTokenHandleDispatcher tokenHandleDispatcher = new OAuthTokenHandleDispatcher(tokenRequest, response);
            tokenHandleDispatcher.dispatch();*/
            AbstractOAuthTokenHandler authTokenHandler = findGrantHandler(tokenRequest);
            if (authTokenHandler != null){
                authTokenHandler.setTokenRequest(tokenRequest);
                authTokenHandler.setResponse(response);
                authTokenHandler.handle(tokenRequest, response);
            }else {
                    unsupportGrantType(tokenRequest, response);
            }


        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }

    }


    /**
     * Find the matching grant handler
     */
    protected AbstractOAuthTokenHandler findGrantHandler(OAuthTokenxRequest oAuthAuthxRequest) {
        String grantType =oAuthAuthxRequest.getGrantType();
        List<AbstractOAuthTokenHandler> oAuthTokenHandlers =
            handlerProvider.getoAuthTokenHandlers();
        if (grantType != null) {
            for (AbstractOAuthTokenHandler handler : oAuthTokenHandlers) {
                if (handler.getSupportedGrantTypes().contains(grantType)) {
                    return handler;
                }
            }
        }
        throw new IllegalStateException("Not found 'OAuthTokenHandler' to handle OAuthTokenxRequest: " + oAuthAuthxRequest);
    }

    private void unsupportGrantType(OAuthTokenxRequest oauthRequest, HttpServletResponse response) throws
        OAuthSystemException {
        final String grantType = oauthRequest.getGrantType();
        LOG.debug("Unsupport response_type '{}' by client_id '{}'", grantType, oauthRequest.getClientId());

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
            .setError(OAuthError.TokenResponse.INVALID_GRANT)
            .setErrorDescription("Unsupport grant_type '" + grantType + "'")
            .buildJSONMessage();
        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
    }
}
