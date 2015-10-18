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
package com.power4j.oauth2.service;


import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.OauthCode;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;

import java.util.Set;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
public interface OauthService {
    /**
     * 获取客户端信息
     * @param clientId
     * @return
     */
    ClientDetails loadClientDetails(String clientId);

    /**
     * 保存授权码
     * @param authCode
     * @param clientDetails
     * @return
     */
    OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails);

    /**
     * retrieveAuthCode
     * @param clientDetails
     * @return
     * @throws OAuthSystemException
     */
    String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException;

    /**
     * retrieveAccessToken
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    ServerAccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    /**
     * retrieveAccessToken
     * @param clientDetails
     * @param scopes
     * @param includeRefreshToken
     * @return
     * @throws OAuthSystemException
     */
    ServerAccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes,
        boolean includeRefreshToken) throws OAuthSystemException;

    /**
     * retrieveNewAccessToken
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    //Always return new ServerAccessToken, exclude refreshToken
    ServerAccessToken retrieveNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    /**
     * 加载授权码
     * @param code
     * @param clientDetails
     * @return
     */
    OauthCode loadOauthCode(String code, ClientDetails clientDetails);

    /**
     * 移除授权码
     * @param code
     * @param clientDetails
     * @return
     */
    boolean removeOauthCode(String code, ClientDetails clientDetails);

    /**
     * retrieveNewAccessToken Always return new ServerAccessToken
     * @param clientDetails
     * @return
     * @throws OAuthSystemException
     */
    //Always return new ServerAccessToken
    ServerAccessToken retrieveNewAccessToken(ClientDetails clientDetails) throws OAuthSystemException;

    /**
     * retrievePasswordAccessToken （grant_type=password）
     * @param clientDetails
     * @param scopes
     * @param username
     * @return
     * @throws OAuthSystemException
     */
    //grant_type=password ServerAccessToken
    ServerAccessToken retrievePasswordAccessToken(ClientDetails clientDetails, Set<String> scopes,
        String username) throws OAuthSystemException;

    /**
     * retrieveClientCredentialsAccessToken （grant_type=client_credentials）
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    //grant_type=client_credentials
    ServerAccessToken retrieveClientCredentialsAccessToken(ClientDetails clientDetails,
        Set<String> scopes) throws OAuthSystemException;

    /**
     * loadAccessTokenByRefreshToken
     * @param refreshToken
     * @param clientId
     * @return
     */
    ServerAccessToken loadAccessTokenByRefreshToken(String refreshToken, String clientId);

    /**
     * changeAccessTokenByRefreshToken
     * @param refreshToken
     * @param clientId
     * @return
     * @throws OAuthSystemException
     */
    ServerAccessToken changeAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException;
}
