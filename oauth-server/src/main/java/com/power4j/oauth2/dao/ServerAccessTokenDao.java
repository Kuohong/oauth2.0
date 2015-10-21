package com.power4j.oauth2.dao;

import org.apache.ibatis.annotations.Param;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;

/**
 * ClassName: org.power4j.oauth2.dao.ServerAccessTokenDao <br>
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface ServerAccessTokenDao {
    ServerAccessToken findAccessToken(@Param("clientId")String clientId, @Param("openid")String openid, @Param("authenticationId")String authenticationId);
    ServerAccessToken findAccessTokenByClientIdAndUserUUID(@Param("clientId")String clientId, @Param("openid")String openid);

    int deleteAccessToken(ServerAccessToken accessToken);

    int saveAccessToken(ServerAccessToken accessToken);

    ServerAccessToken findAccessTokenByRefreshToken(@Param("refreshToken")String refreshToken, @Param("clientId")String clientId);

    ServerAccessToken selectAccessTokenByTokenId(@Param("tokenId") String tokenId);
}
