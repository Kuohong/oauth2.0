package org.power4j.oauth2.dao;

import org.apache.ibatis.annotations.Param;
import org.power4j.oauth2.common.pojo.OauthCode;

/**
 * ClassName: org.power4j.oauth2.dao <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface OauthCodeDao {
    int saveOauthCode(OauthCode oauthCode);

    OauthCode findOauthCode(@Param("code") String code, @Param("clientId")String clientId);

    OauthCode findOauthCodeByUsernameClientId(@Param("username") String username, @Param("clientId") String clientId);

    int deleteOauthCode(OauthCode oauthCode);
}
