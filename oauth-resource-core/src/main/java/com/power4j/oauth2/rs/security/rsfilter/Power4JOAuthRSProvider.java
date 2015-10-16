package com.power4j.oauth2.rs.security.rsfilter;

import com.power4j.framework.context.util.BeanProvider;
import com.power4j.oauth2.common.pojo.CommonStatus;
import com.power4j.oauth2.rs.security.OAuthPrincipal;
import com.power4j.oauth2.rs.security.rsfilter.pojo.RSOAuthClient;
import com.power4j.oauth2.rs.service.OAuthRSService;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.rsfilter.OAuthDecision;
import org.apache.oltu.oauth2.rsfilter.OAuthRSProvider;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: com.power4j.oauth2.resource.security.rsfilter <br>
 *
 * @author Kuo Hong
 * @version 2015-10-15
 */
public class Power4JOAuthRSProvider implements OAuthRSProvider {

    private static final Logger LOG = LoggerFactory.getLogger(Power4JOAuthRSProvider.class);


    private transient OAuthRSService rsService = BeanProvider.getBean(OAuthRSService.class);

    @Override
    public OAuthDecision validateRequest(String rsId, String token, HttpServletRequest req) throws
        OAuthProblemException {
        LOG.debug("Call OAuthRSProvider, rsId: {},token: {},req: {}", new Object[]{rsId, token, req});

        ServerAccessToken accessToken = rsService.loadAccessTokenByTokenId(token);
        validateToken(token, accessToken);

        ClientDetails clientDetails = rsService.loadClientDetails(accessToken.clientId(), rsId);
        validateClientDetails(token, accessToken, clientDetails);


        //TODO: It is OK?
        Power4JOAuthDecision oAuthDecision = new Power4JOAuthDecision().setOAuthClient(new RSOAuthClient(clientDetails));
        oAuthDecision.setPrincipal(new OAuthPrincipal(accessToken.username()));
        oAuthDecision.setAuthorized(true);

        return oAuthDecision;
    }

    private void validateClientDetails(String token, ServerAccessToken accessToken, ClientDetails clientDetails) throws OAuthProblemException {
        if (clientDetails == null || !clientDetails.getStatus().equals(CommonStatus.ENABLE)) {
            LOG.debug("Invalid ClientDetails: {} by client_id: {}, it is null or archived", clientDetails, accessToken.clientId());
            throw OAuthProblemException.error(OAuthError.ResourceResponse.INVALID_TOKEN)
                .description("Invalid client by token: " + token);
        }
    }

    private void validateToken(String token, ServerAccessToken accessToken) throws OAuthProblemException {
        if (accessToken == null) {
            LOG.debug("Invalid access_token: {}, because it is null", token);
            throw OAuthProblemException.error(OAuthError.ResourceResponse.INVALID_TOKEN)
                .description("Invalid access_token: " + token);
        }
        if (accessToken.isTokenExpired()) {
            LOG.debug("Invalid access_token: {}, because it is expired", token);
            throw OAuthProblemException.error(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                .description("Expired access_token: " + token);
        }
    }
}
