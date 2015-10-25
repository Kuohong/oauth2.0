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
import com.power4j.oauth2.validator.AbstractClientDetailsValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.validator.ClientCredentialsClientDetailsValidator;
import com.power4j.oauth2.web.wapper.OAuthTokenxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**

 * grant_type=client_credentials
 *
 * @author Kuo Hong
 */
public class ClientCredentialsTokenHandler extends AbstractOAuthTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ClientCredentialsTokenHandler.class);


    @Override
    public boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.CLIENT_CREDENTIALS.toString().equalsIgnoreCase(grantType);
    }

    /**
     * /oauth/token?client_id=credentials-client&client_secret=credentials-secret&grant_type=client_credentials&scope=read write
     * <p/>
     * Response access_token
     * If exist BearAccessToken and it is not expired, return it
     * otherwise, return a new BearAccessToken
     * <p/>
     * {"access_token":"38187f32-e4fb-4650-8e4a-99903b66f20e","token_type":"bearer","expires_in":7}
     */
    @Override
    public void handleAfterValidation() throws OAuthProblemException, OAuthSystemException {

        ServerAccessToken accessToken = oauthService.retrieveClientCredentialsAccessToken(
            clientDetails(), tokenRequest.getScopes());
        final OAuthResponse tokenResponse = createTokenResponse(accessToken, false);

        LOG.debug("'client_credentials' response: {}", tokenResponse);
        WebUtils.writeOAuthJsonResponse(response, tokenResponse);


    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new ClientCredentialsClientDetailsValidator(tokenRequest);
    }

    @Override public List<String> getSupportedGrantTypes() {
        List<String> supportedGrantTypes = new ArrayList<>();
        supportedGrantTypes.add("client_credentials");
        return supportedGrantTypes;
    }

}
