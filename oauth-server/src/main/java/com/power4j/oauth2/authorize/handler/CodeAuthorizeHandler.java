package com.power4j.oauth2.authorize.handler;


import com.power4j.oauth2.common.constants.OAuthConstants;
import com.power4j.oauth2.common.web.util.WebUtils;
import com.power4j.oauth2.web.wapper.OAuthAuthxRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.validator.AbstractClientDetailsValidator;
import com.power4j.oauth2.validator.CodeClientDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Handle response_type = 'code'
 *
 * @author Kuo Hong
 */
public class CodeAuthorizeHandler extends AbstractAuthorizeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CodeAuthorizeHandler.class);
    public CodeAuthorizeHandler(){
        super();
    }

    public CodeAuthorizeHandler(OAuthAuthxRequest oauthRequest, HttpServletResponse response) {
        super(oauthRequest, response);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new CodeClientDetailsValidator(oauthRequest);
    }


    //response code
    @Override
    protected void handleResponse() throws OAuthSystemException, IOException {
        final ClientDetails clientDetails = clientDetails();
        final String authCode = oauthService.retrieveAuthCode(clientDetails);

        final OAuthResponse oAuthResponse = OAuthASResponse
                .authorizationResponse(oauthRequest.request(), HttpServletResponse.SC_OK)
                .location(oauthRequest.getRedirectURI())
                .setCode(authCode)
                .buildQueryMessage();
        oAuthResponse.setBody(new StringBuffer("{").append("code:").append(authCode).append("}").toString());
        LOG.debug(" 'code' response: {}", oAuthResponse);
        WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
    }


    @Override public List<String> getSupportedGrantTypes() {
        List<String> supportedGrantTypes = new ArrayList<>();
        supportedGrantTypes.add("code");
        return supportedGrantTypes;
    }
}
