package com.power4j.oauth2.service.impl;

import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.CommonStatus;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.common.pojo.token.bearer.BearerAccessToken;
import com.power4j.oauth2.dao.ClientDetailsDao;
import com.power4j.oauth2.dao.ServerAccessTokenDao;
import com.power4j.oauth2.service.OauthService;
import com.power4j.oauth2.support.AuthenticationIdGenerator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import com.power4j.oauth2.common.pojo.OauthCode;
import com.power4j.oauth2.dao.OauthCodeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * ClassName: org.power4j.oauth2.service.impl.OauthServiceImpl <br>
 *
 * @author Kuo Hong
 */
@Service
public class OauthServiceImpl implements OauthService {
    private Logger logger = LoggerFactory.getLogger(OauthServiceImpl.class);
    @Resource
    private OAuthIssuer oAuthIssuer;
    @Resource ServerAccessTokenDao serverAccessTokenDao;
    @Resource ClientDetailsDao clientDetailsDao;
    @Resource
    OauthCodeDao oauthCodeDao;
    @Resource
    private AuthenticationIdGenerator authenticationIdGenerator;

    protected String currentUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
    private ServerAccessToken createAndSaveAccessToken(ClientDetails clientDetails, boolean includeRefreshToken, String openid, String authenticationId) throws OAuthSystemException {
        ServerAccessToken accessToken = new BearerAccessToken();
        accessToken.setClientId(clientDetails.getClientId());
        accessToken.setOpenid(openid);
        accessToken .setTokenId(oAuthIssuer.accessToken());
        accessToken.setAuthenticationId(authenticationId);
        accessToken .updateByClientDetails(clientDetails);

        if (includeRefreshToken) {
            accessToken.refreshToken(oAuthIssuer.refreshToken());
        }
        accessToken.setCreateTime(new Date());
        this.serverAccessTokenDao.saveAccessToken(accessToken);
        logger.debug("Save AccessToken: {}", accessToken);
        return accessToken;
    }
    /**
     * 获取客户端信息
     *
     * @param clientId
     * @return
     */
    @Override public ClientDetails loadClientDetails(String clientId) {

        logger.debug("Load ClientDetails by clientId: {}", clientId);
        return clientDetailsDao.findClientDetails(clientId , CommonStatus.ENABLE);
    }

    /**
     * 保存授权码
     *
     * @param authCode
     * @param clientDetails
     * @return
     */
    @Override public OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails) {
        final String username = currentUsername();
        OauthCode oauthCode = new OauthCode()
            .code(authCode).openId(username)
            .clientId(clientDetails.getClientId());
        oauthCodeDao.saveOauthCode(oauthCode);
        return oauthCode;
    }

    /**
     * retrieveAuthCode
     *
     * @param clientDetails
     * @return
     * @throws OAuthSystemException
     */
    @Override public String retrieveAuthCode(ClientDetails clientDetails)
        throws OAuthSystemException {
        final String clientId = clientDetails.getClientId();
        final String username = currentUsername();

        OauthCode oauthCode = oauthCodeDao.findOauthCodeByUsernameClientId(username, clientId);
        if (oauthCode != null) {
            //Always delete exist
            logger.debug("OauthCode ({}) is existed, remove it and create a new one", oauthCode);
            oauthCodeDao.deleteOauthCode(oauthCode);
        }
        //create a new one
        oauthCode = createOauthCode(clientDetails);

        return oauthCode.code();
    }

    protected OauthCode createOauthCode(ClientDetails clientDetails) throws OAuthSystemException {
        OauthCode oauthCode;
        final String authCode = oAuthIssuer.authorizationCode();

        logger.debug("Save authorizationCode '{}' of ClientDetails '{}'", authCode, clientDetails);
        oauthCode = this.saveAuthorizationCode(authCode, clientDetails);
        return oauthCode;
    }

    /**
     * retrieveAccessToken
     *
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken retrieveAccessToken(ClientDetails clientDetails,
        Set<String> scopes) throws OAuthSystemException {
        return retrieveAccessToken(clientDetails, scopes, clientDetails.isSupportRefreshToken());
    }

    /**
     * retrieveAccessToken
     *
     * @param clientDetails
     * @param scopes
     * @param includeRefreshToken
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken retrieveAccessToken(ClientDetails clientDetails,
        Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = currentUsername();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);

        ServerAccessToken accessToken = serverAccessTokenDao.findAccessToken(clientId, username, authenticationId);
        if (accessToken == null) {
            accessToken = createAndSaveAccessToken(clientDetails, includeRefreshToken, username, authenticationId);
            logger.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }

    /**
     * retrieveNewAccessToken
     *
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken retrieveNewAccessToken(ClientDetails clientDetails,
        Set<String> scopes) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = currentUsername();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);

        ServerAccessToken accessToken = serverAccessTokenDao.findAccessToken(clientId, username, authenticationId);
        if (accessToken != null) {
            logger.debug("Delete existed AccessToken: {}", accessToken);
            serverAccessTokenDao.deleteAccessToken(accessToken);
        }
        accessToken = createAndSaveAccessToken(clientDetails, false, username, authenticationId);
        logger.debug("Create a new AccessToken: {}", accessToken);

        return accessToken;
    }

    /**
     * 加载授权码
     *
     * @param code
     * @param clientDetails
     * @return
     */
    @Override public OauthCode loadOauthCode(String code, ClientDetails clientDetails) {
        final String clientId = clientDetails.getClientId();
        return oauthCodeDao.findOauthCode(code, clientId);
    }

    /**
     * 移除授权码
     *
     * @param code
     * @param clientDetails
     * @return
     */
    @Override public boolean removeOauthCode(String code, ClientDetails clientDetails) {
        final OauthCode oauthCode = loadOauthCode(code, clientDetails);
        final int rows = oauthCodeDao.deleteOauthCode(oauthCode);
        return rows > 0;
    }

    /**
     * retrieveNewAccessToken Always return new ServerAccessToken
     *
     * @param clientDetails
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken retrieveNewAccessToken(ClientDetails clientDetails)
        throws OAuthSystemException {
        final String username = currentUsername();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, null);

        ServerAccessToken accessToken = serverAccessTokenDao.findAccessToken(clientId, username, authenticationId);
        if (accessToken != null) {
            logger.debug("Delete existed AccessToken: {}", accessToken);
            serverAccessTokenDao.deleteAccessToken(accessToken);
        }
        accessToken = createAndSaveAccessToken(clientDetails, clientDetails.isSupportRefreshToken(), username, authenticationId);
        logger.debug("Create a new AccessToken: {}", accessToken);

        return accessToken;
    }

    /**
     * retrievePasswordAccessToken （grant_type=password）
     *
     * @param clientDetails
     * @param scopes
     * @param username
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken retrievePasswordAccessToken(ClientDetails clientDetails,
        Set<String> scopes, String username) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);
        ServerAccessToken accessToken = serverAccessTokenDao.findAccessTokenByClientIdAndUserUUID(clientId, username);

        boolean needCreate = false;
        if (accessToken == null) {
            needCreate = true;
            logger.debug("Not found AccessToken from repository, will create a new one, client_id: {}", clientId);
        } else if (accessToken.isTokenExpired()) {
            logger.debug("Delete expired AccessToken: {} and create a new one, client_id: {}", accessToken, clientId);
            serverAccessTokenDao.deleteAccessToken(accessToken);
            needCreate = true;
        } else {
            logger.debug("Use existed AccessToken: {}, client_id: {}", accessToken, clientId);
        }

        if (needCreate) {
            accessToken = createAndSaveAccessToken(clientDetails, clientDetails.isSupportRefreshToken(), username, authenticationId);
            logger.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }

    /**
     * retrieveClientCredentialsAccessToken （grant_type=client_credentials）
     *
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken retrieveClientCredentialsAccessToken(
        ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String clientId = clientDetails.getClientId();
        //username = clientId

        final String authenticationId = authenticationIdGenerator.generate(clientId, clientId, scope);
        ServerAccessToken accessToken = serverAccessTokenDao.findAccessToken(clientId, clientId, authenticationId);

        boolean needCreate = false;
        if (accessToken == null) {
            needCreate = true;
            logger.debug("Not found AccessToken from repository, will create a new one, client_id: {}", clientId);
        } else if (accessToken.isTokenExpired()) {
            logger.debug("Delete expired AccessToken: {} and create a new one, client_id: {}", accessToken, clientId);
            serverAccessTokenDao.deleteAccessToken(accessToken);
            needCreate = true;
        } else {
            logger.debug("Use existed AccessToken: {}, client_id: {}", accessToken, clientId);
        }

        if (needCreate) {
            //Ignore refresh_token
            accessToken = createAndSaveAccessToken(clientDetails, false, clientId, authenticationId);
            logger.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }

    /**
     * loadAccessTokenByRefreshToken
     *
     * @param refreshToken
     * @param clientId
     * @return
     */
    @Override public ServerAccessToken loadAccessTokenByRefreshToken(String refreshToken,
        String clientId) {
        logger.debug("Load ClientDetails by refreshToken: {} and clientId: {}", refreshToken, clientId);
        return serverAccessTokenDao.findAccessTokenByRefreshToken(refreshToken, clientId);
    }

    /**
     * changeAccessTokenByRefreshToken
     *
     * @param refreshToken
     * @param clientId
     * @return
     * @throws OAuthSystemException
     */
    @Override public ServerAccessToken changeAccessTokenByRefreshToken(String refreshToken,
        String clientId) throws OAuthSystemException {
        final ServerAccessToken oldToken = loadAccessTokenByRefreshToken(refreshToken, clientId);
        ServerAccessToken newAccessToken = oldToken.cloneMe();
        logger.debug("Create new AccessToken: {} from old AccessToken: {}", newAccessToken,
            oldToken);

        ClientDetails details = clientDetailsDao.findClientDetails(clientId, CommonStatus.ENABLE);
        newAccessToken.updateByClientDetails(details);

        final String authId = authenticationIdGenerator.generate(clientId, oldToken.getOpenid(), null);
        newAccessToken.authenticationId(authId)
            .tokenId(oAuthIssuer.accessToken())
            .refreshToken(oAuthIssuer.refreshToken());

        newAccessToken.setCreateTime(new Date());
        serverAccessTokenDao.deleteAccessToken(oldToken);
        logger.debug("Delete old AccessToken: {}", oldToken);

        serverAccessTokenDao.saveAccessToken(newAccessToken);
        logger.debug("Save new AccessToken: {}", newAccessToken);

        return newAccessToken;
    }
}
