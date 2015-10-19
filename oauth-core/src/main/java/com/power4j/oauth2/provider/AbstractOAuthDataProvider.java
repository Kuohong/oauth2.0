package com.power4j.oauth2.provider;

import com.power4j.oauth2.common.AccessTokenRegistration;
import com.power4j.oauth2.common.constants.OAuthConstants;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.common.pojo.token.bearer.BearerAccessToken;
import com.power4j.oauth2.common.pojo.token.refresh.RefreshToken;
import com.power4j.oauth2.common.util.OauthUtils;
import com.power4j.oauth2.exception.OAuthServiceException;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.util.List;

/**
 * ClassName: com.power4j.oauth2.provider.AbstractOAuthDataProvider <br>
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public abstract class AbstractOAuthDataProvider implements OAuthDataProvider{

    protected OAuthIssuer oAuthIssuer;
    protected abstract void saveAccessToken(ServerAccessToken serverToken);
    protected abstract void saveRefreshToken(ServerAccessToken at, RefreshToken refreshToken);

    protected abstract boolean revokeAccessToken(String accessTokenKey);

    protected abstract RefreshToken revokeRefreshToken(ClientDetails client, String refreshTokenKey);
    protected ServerAccessToken createNewAccessToken(AccessTokenRegistration accessTokenRegistration) {
        BearerAccessToken serverAccessToken = null;
        try {
            serverAccessToken = new BearerAccessToken(accessTokenRegistration.getClient(),oAuthIssuer.accessToken(),
                OAuthConstants.ACCESS_TOKEN_VALIDITY_SECONDS,
                OauthUtils.getIssuedAt());
        } catch (OAuthSystemException e) {
            throw new OAuthServiceException(e);
        }
        return serverAccessToken;
    }
    @Override
    public ServerAccessToken createAccessToken(AccessTokenRegistration accessTokenRegistration)
        throws OAuthServiceException {
        return doCreateAccessToken(accessTokenRegistration);
    }
    protected boolean isRefreshTokenSupported(List<String> theScopes) {
        return theScopes.contains(OAuthConstants.REFRESH_TOKEN_SCOPE);
    }

    protected RefreshToken createNewRefreshToken(ServerAccessToken at) {
        //TODO createNewRefreshToken
       RefreshToken rt = null;
        try {
            rt = new RefreshToken(at.getClient(), oAuthIssuer.refreshToken(), OAuthConstants.REFRESH_TOKEN_VALIDITY_SECONDS);
        } catch (OAuthSystemException e) {
            throw new OAuthServiceException(OAuthConstants.SERVER_ERROR);
        }
        //rt.setAudience(at.getAudience());
        rt.setGrantType(at.getGrantType());
        //rt.setScopes(at.getScopes());
        //rt.setSubject(at.getSubject());
        rt.getAccessTokens().add(at.getTokenId());
        at.setRefreshToken(rt.getTokenId());
        saveRefreshToken(at, rt);
        return rt;
    }

    private ServerAccessToken doCreateAccessToken(AccessTokenRegistration accessTokenRegistration) {
        ServerAccessToken at = createNewAccessToken(accessTokenRegistration);
        at.setGrantType(accessTokenRegistration.getGrantType());
        List<String> theScopes = accessTokenRegistration.getApprovedScope();
        saveAccessToken(at);
        if (accessTokenRegistration.getClient().isSupportRefreshToken()) {
            createNewRefreshToken(at);
        }
        return at;
    }

    @Override
    public void revokeToken(ClientDetails client, String tokenKey, String tokenTypeHint)  {
        if (revokeAccessToken(tokenKey)) {
            return;
        }
        revokeRefreshAndAccessTokens(client, tokenKey);
    }
    protected RefreshToken revokeRefreshAndAccessTokens(ClientDetails client, String tokenKey) {
        RefreshToken oldRefreshToken = revokeRefreshToken(client, tokenKey);
        if (oldRefreshToken != null) {
            for (String accessTokenKey : oldRefreshToken.getAccessTokens()) {
                revokeAccessToken(accessTokenKey);
            }
        }
        return oldRefreshToken;
    }
}
