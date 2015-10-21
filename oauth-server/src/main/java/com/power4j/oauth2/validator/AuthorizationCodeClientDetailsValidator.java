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
package com.power4j.oauth2.validator;


import com.power4j.oauth2.common.constants.OAuthConstants;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.OauthCode;
import com.power4j.oauth2.web.wapper.OAuthTokenxRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kuo Hong
 */
public class AuthorizationCodeClientDetailsValidator extends AbstractOauthTokenValidator {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeClientDetailsValidator.class);


    public AuthorizationCodeClientDetailsValidator(OAuthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }

    /**
     * /oauth2.0/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=redirect_uri
     */
    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //validate grant_type
        final String grantType = grantType();
        if (!clientDetails.getGrantTypes().contains(grantType)) {
            LOG.debug("Invalid grant_type '{}', client_id = '{}'", grantType, clientDetails.getClientId());
            return invalidGrantTypeResponse(grantType);
        }

        //validate client_secret
        final String clientSecret = oauthRequest.getClientSecret();
        if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
            LOG.debug("Invalid client_secret '{}', client_id = '{}'", clientSecret, clientDetails.getClientId());
            return invalidClientSecretResponse();
        }


        //validate redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        boolean validate = redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri()) || redirectURI.equals(
            OAuthConstants.OOB_RESPONSE);
        if (!validate) {
            LOG.debug("Invalid redirect_uri '{}', client_id = '{}'", redirectURI, clientDetails.getClientId());
            return invalidRedirectUriResponse();
        }

        //validate code
        String code = getCode();
        OauthCode oauthCode = oauthService.loadOauthCode(code, clientDetails());
        if (oauthCode == null) {
            LOG.debug("Invalid code '{}', client_id = '{}'", code, clientDetails.getClientId());
            return invalidCodeResponse(code);
        }

        return null;
    }

    private OAuthResponse invalidCodeResponse(String code) throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Invalid code '" + code + "'")
                .buildJSONMessage();
    }

    private String getCode() {
        return ((OAuthTokenxRequest) oauthRequest).getCode();
    }
}
