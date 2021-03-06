package com.power4j.oauth2.web.handler;

import com.power4j.oauth2.common.web.util.WebUtils;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cute on 2015/10/13.
 */
public class OAuthShiroHandlerExceptionResolver implements HandlerExceptionResolver {


    public OAuthShiroHandlerExceptionResolver() {
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if (ex instanceof UnauthorizedException) {
            handleUnauthorizedException(response, ex);
        } else if (ex instanceof AuthorizationException) {
            handleUnauthorizedException(response, ex);
        }
        //more

        return null;
    }

    private void handleUnauthorizedException(HttpServletResponse response, Exception ex) {
        OAuthResponse oAuthResponse;
        try {
            oAuthResponse = OAuthResponse.errorResponse(403)
                .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                .setErrorDescription(ex.getMessage())
                .buildJSONMessage();
        } catch (OAuthSystemException e) {
            throw new IllegalStateException(e);
        }

        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
    }

}
