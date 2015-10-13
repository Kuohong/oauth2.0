package org.power4j.oauth2.dao;

import org.apache.ibatis.annotations.Param;
import org.power4j.oauth2.common.pojo.token.ServerAccessToken;

/**
 * ClassName: org.power4j.oauth2.dao.ServerAccessTokenDao <br>
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface ServerAccessTokenDao {
    ServerAccessToken findAccessToken(@Param("clientId")String clientId, @Param("username")String username, @Param("authenticationId")String authenticationId);

    int deleteAccessToken(ServerAccessToken accessToken);

    int saveAccessToken(ServerAccessToken accessToken);

    ServerAccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId);
}
