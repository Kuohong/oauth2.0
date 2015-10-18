package com.power4j.oauth2.service;

import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;

/**
 * ClassName: com.power4j.oauth2.service.OAuthRSService <br>
 *
 * @author Kuo Hong
 * @version 2015-10-14
 */
public interface OAuthRSService {
    ServerAccessToken loadAccessTokenByTokenId(String tokenId);

    ClientDetails loadClientDetails(String clientId, String resourceIds);
}
