package com.power4j.oauth2.rs.security.filter;


import com.power4j.oauth2.rs.service.OAuthRSService;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.common.web.util.WebUtils;
import com.power4j.oauth2.rs.security.OAuth2Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: com.power4j.oauth2.service.security.filter <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-14
 */
public class OAuth2Filter extends AuthenticatingFilter implements InitializingBean {


    private static Logger logger = LoggerFactory.getLogger(OAuth2Filter.class);


    //   ParameterStyle.HEADER
    //    private ParameterStyle[] parameterStyles = new ParameterStyle[]{ParameterStyle.QUERY};

    private String resourceId;
    private OAuthRSService rsService;


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String accessToken = httpRequest.getParameter(OAuth.OAUTH_ACCESS_TOKEN);
        final ServerAccessToken token = rsService.loadAccessTokenByTokenId(accessToken);

        String username = null;
        if (token != null) {
            logger.debug("Set username and clientId from AccessToken: {}", token);
            username = token.getUsername();
            httpRequest.setAttribute(OAuth.OAUTH_CLIENT_ID, token.clientId());
        } else {
            logger.debug("Not found AccessToken by access_token: {}", accessToken);
        }

        return new OAuth2Token(accessToken, resourceId)
            .setUserId(username);
    }


    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
        ServletResponse response) {
        OAuth2Token oAuth2Token = (OAuth2Token) token;

        final OAuthResponse oAuthResponse;
        try {
            oAuthResponse = OAuthRSResponse.errorResponse(401)
                .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                .setErrorDescription(ae.getMessage())
                .buildJSONMessage();

            WebUtils.writeOAuthJsonResponse((HttpServletResponse) response, oAuthResponse);

        } catch (OAuthSystemException e) {
            logger.error("Build JSON message error", e);
            throw new IllegalStateException(e);
        }


        return false;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setRsService(OAuthRSService rsService) {
        this.rsService = rsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(resourceId, "resourceId is null");
        Assert.notNull(rsService, "rsService is null");
    }
}
