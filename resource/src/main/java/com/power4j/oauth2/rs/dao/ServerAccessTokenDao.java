package com.power4j.oauth2.rs.dao;

import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: com.power4j.oauth2.rs.dao <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-15
 */
public interface ServerAccessTokenDao {
    ServerAccessToken findAccessTokenByTokenId(@Param("tokenId") String tokenId);
}
