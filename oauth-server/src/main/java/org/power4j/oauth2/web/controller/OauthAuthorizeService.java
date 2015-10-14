package org.power4j.oauth2.web.controller;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.power4j.oauth2.authorize.handler.CodeAuthorizeHandler;
import org.power4j.oauth2.authorize.handler.TokenAuthorizeHandler;
import org.power4j.oauth2.web.wapper.OAuthAuthxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: org.power4j.web.rs <br>
 *
 * @author Kuo Hong
 * @version 2015-10-11
 */
@Controller
@RequestMapping("oauth2.0/")
public class OauthAuthorizeService {
    private static final Logger LOG = LoggerFactory.getLogger(OauthAuthorizeService.class);

    /**
     * Must handle the grant_type as follow:
     * grant_type="authorization_code" -> response_type="code"
     * ?response_type=code&scope=read,write&client_id=[client_id]&redirect_uri=[redirect_uri]&state=[state]
     * <p/>
     * grant_type="implicit"   -> response_type="token"
     * ?response_type=token&scope=read,write&client_id=[client_id]&client_secret=[client_secret]&redirect_uri=[redirect_uri]
     * <p/>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping("authorize")
    public void authorize(HttpServletRequest request,HttpServletResponse response)
        throws OAuthSystemException, ServletException, IOException {
         startAuthorization(request, response);
    }

    private void startAuthorization( HttpServletRequest request, HttpServletResponse response)
        throws OAuthSystemException, ServletException, IOException {
        try {
            OAuthAuthxRequest oauthRequest = new OAuthAuthxRequest(request);
            if (oauthRequest.isCode()) {
                CodeAuthorizeHandler codeAuthorizeHandler = new CodeAuthorizeHandler(oauthRequest, response);
                LOG.debug("Go to  response_type = 'code' handler: {}", codeAuthorizeHandler);
                codeAuthorizeHandler.handle();

            } else if (oauthRequest.isToken()) {
                TokenAuthorizeHandler tokenAuthorizeHandler = new TokenAuthorizeHandler(oauthRequest, response);
                LOG.debug("Go to response_type = 'token' handler: {}", tokenAuthorizeHandler);
                tokenAuthorizeHandler.handle();

            } else {
                unsupportResponseType(oauthRequest, response);
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

    private void unsupportResponseType(OAuthAuthxRequest oauthRequest, HttpServletResponse response) throws OAuthSystemException {
        final String responseType = oauthRequest.getResponseType();
        LOG.debug("Unsupport response_type '{}' by client_id '{}'", responseType, oauthRequest.getClientId());

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
            .setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
            .setErrorDescription("Unsupport response_type '" + responseType + "'")
            .buildJSONMessage();
        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
    }




}
