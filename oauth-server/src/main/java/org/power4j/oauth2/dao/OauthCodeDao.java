package org.power4j.oauth2.dao;

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

    OauthCode findOauthCode(String code, String clientId);

    OauthCode findOauthCodeByUsernameClientId(String username, String clientId);

    int deleteOauthCode(OauthCode oauthCode);
}
