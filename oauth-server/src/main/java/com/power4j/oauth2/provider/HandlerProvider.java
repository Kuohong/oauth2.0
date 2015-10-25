package com.power4j.oauth2.provider;

import com.power4j.oauth2.authorize.handler.AbstractAuthorizeHandler;
import com.power4j.oauth2.token.handler.AbstractOAuthTokenHandler;
import com.power4j.oauth2.web.wapper.OAuthAuthxRequest;
import com.power4j.oauth2.web.wapper.OAuthTokenxRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: com.power4j.oauth2.provider <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-25
 */
public class HandlerProvider {
    private List<AbstractAuthorizeHandler> authorizeHandlers = new ArrayList<>();
    private List<AbstractOAuthTokenHandler> oAuthTokenHandlers = new ArrayList<>();

    public List<AbstractAuthorizeHandler> getAuthorizeHandlers() {
        return authorizeHandlers;
    }

    public void setAuthorizeHandlers(List<AbstractAuthorizeHandler> authorizeHandlers) {
        this.authorizeHandlers = authorizeHandlers;
    }

    public List<AbstractOAuthTokenHandler> getoAuthTokenHandlers() {
        return oAuthTokenHandlers;
    }

    public void setoAuthTokenHandlers(List<AbstractOAuthTokenHandler> oAuthTokenHandlers) {
        this.oAuthTokenHandlers = oAuthTokenHandlers;
    }

    /**
     * Find the matching grant handler
     */
    protected AbstractAuthorizeHandler findGrantHandler(OAuthAuthxRequest oAuthAuthxRequest) {
        String grantType =oAuthAuthxRequest.getResponseType();

        if (grantType != null) {
            for (AbstractAuthorizeHandler handler : authorizeHandlers) {
                if (handler.getSupportedGrantTypes().contains(grantType)) {
                    return handler;
                }
            }
        }

        return null;
    }

    /**
     * Find the matching grant handler
     */
    protected AbstractOAuthTokenHandler findGrantHandler(OAuthTokenxRequest oAuthAuthxRequest) {
        String grantType =oAuthAuthxRequest.getGrantType();

        if (grantType != null) {
            for (AbstractOAuthTokenHandler handler : oAuthTokenHandlers) {
                if (handler.getSupportedGrantTypes().contains(grantType)) {
                    return handler;
                }
            }
        }
        throw new IllegalStateException("Not found 'OAuthTokenHandler' to handle OAuthTokenxRequest: " + oAuthAuthxRequest);
    }
}
