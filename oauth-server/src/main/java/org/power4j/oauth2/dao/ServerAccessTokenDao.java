package org.power4j.oauth2.dao;

import org.power4j.oauth2.common.pojo.token.ServerAccessToken;

/**
 * ClassName: org.power4j.oauth2.dao.ServerAccessTokenDao <br>
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface ServerAccessTokenDao {
    ServerAccessToken findAccessToken(String clientId, String username, String authenticationId);

    int deleteAccessToken(ServerAccessToken accessToken);

    int saveAccessToken(ServerAccessToken accessToken);

    ServerAccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId);
}
