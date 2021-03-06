package com.power4j.oauth2.validator;


import com.power4j.oauth2.common.constants.OAuthConstants;
import com.power4j.oauth2.common.pojo.ClientDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 *
 * @author Kuo Hong
 */
public class CodeClientDetailsValidator extends AbstractClientDetailsValidator {

    private static final Logger LOG = LoggerFactory.getLogger(CodeClientDetailsValidator.class);

    public CodeClientDetailsValidator(OAuthAuthzRequest oauthRequest) {
        super(oauthRequest);
    }

    /**
    *  grant_type="authorization_code"
    *  ?response_type=code&scope=read,write&client_id=[client_id]&redirect_uri=[redirect_uri]&state=[state]
    * */
    @Override
    public OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {
        //validate redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        boolean validate = redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri()) || redirectURI.equals(
            OAuthConstants.OOB_RESPONSE);
        if (!validate) {
            LOG.debug("Invalid redirect_uri '{}' by response_type = 'code', client_id = '{}'", redirectURI, clientDetails.getClientId());
            return invalidRedirectUriResponse();
        }

        //validate scope
        final Set<String> scopes = oauthRequest.getScopes();
        if (scopes.isEmpty() || excludeScopes(scopes, clientDetails)) {
            return invalidScopeResponse();
        }

        //validate state
        final String state = getState();
        if (StringUtils.isEmpty(state)) {
            LOG.debug("Invalid 'state', it is required, but it is empty");
            return invalidStateResponse();
        }

        return null;
    }

    private String getState() {
        return ((OAuthAuthzRequest) oauthRequest).getState();
    }

    private OAuthResponse invalidStateResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                .setErrorDescription("Parameter 'state'  is required")
                .buildJSONMessage();
    }


}
