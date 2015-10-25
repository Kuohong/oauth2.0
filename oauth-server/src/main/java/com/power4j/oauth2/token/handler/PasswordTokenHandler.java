/*
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
 */
package com.power4j.oauth2.token.handler;


import com.power4j.oauth2.common.web.util.WebUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.validator.AbstractClientDetailsValidator;
import com.power4j.oauth2.validator.PasswordClientDetailsValidator;
import com.power4j.oauth2.web.wapper.OAuthTokenxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * grant_type=password
 *
 * @author Kuo Hong
 */
public class PasswordTokenHandler extends AbstractOAuthTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordTokenHandler.class);


    @Override
    public boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.PASSWORD.toString().equalsIgnoreCase(grantType);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new PasswordClientDetailsValidator(tokenRequest);
    }

    /**
     * /oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
     * <p/>
     * Response access_token
     * If exist BearAccessToken and it is not expired, return it
     * otherwise, return a new BearAccessToken(include refresh_token)
     * <p/>
     * {"token_type":"Bearer","expires_in":33090,"refresh_token":"976aeaf7df1ee998f98f15acd1de63ea","access_token":"7811aff100eb7dadec132f45f1c01727"}
     */
    @Override
    public void handleAfterValidation() throws OAuthProblemException, OAuthSystemException {

        ServerAccessToken accessToken = oauthService.retrievePasswordAccessToken(clientDetails(),
                tokenRequest.getScopes(), tokenRequest.getUsername());
        final OAuthResponse tokenResponse = createTokenResponse(accessToken, false);

        LOG.debug("'password' response: {}", tokenResponse);
        WebUtils.writeOAuthJsonResponse(response, tokenResponse);

    }

    @Override public List<String> getSupportedGrantTypes() {
        List<String> supportedGrantTypes = new ArrayList<>();
        supportedGrantTypes.add("password");
        return supportedGrantTypes;
    }
}
