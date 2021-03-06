package com.power4j.oauth2.common.util;

import com.power4j.oauth2.common.constants.OAuthConstants;

/**
 * ClassName: org.power4j.common.util.OauthUtils <br>
 *
 * @author Kuo Hong
 * @version 2015-10-11
 */
public class OauthUtils {

    public static long getIssuedAt() {
        return System.currentTimeMillis() / OAuthConstants.THOUSAND;
    }
}
