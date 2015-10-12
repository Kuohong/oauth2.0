package org.power4j.oauth2.common.constants;

/**
 * ClassName: org.power4j.common.constants.OauthConstants <br>
 * OauthConstants
 *
 * @author Kuo Hong
 * @version 2015-10-11
 */
public class OauthConstants {
    public static final String REQUEST_USERNAME = "username";
    public static final String REQUEST_PASSWORD = "password";

    public static final String REQUEST_USER_OAUTH_APPROVAL = "user_oauth_approval";

    public static final String OAUTH_LOGIN_VIEW = "oauth_login";
    public static final String OAUTH_APPROVAL_VIEW = "oauth_approval";

    // Well-known token types
    public static final String BEARER_TOKEN_TYPE = "bearer";
    public static final String HAWK_TOKEN_TYPE = "hawk";

    public static final String SERVER_ERROR = "server_error";


    // Mac/Hawk HMAC algorithm names
    public static final String HMAC_ALGO_SHA_1 = "hmac-sha-1";
    public static final String HMAC_ALGO_SHA_256 = "hmac-sha-256";

    // Hawk token parameters
    // Set by Access Token Service
    public static final String HAWK_TOKEN_KEY = "secret";
    public static final String HAWK_TOKEN_ALGORITHM = "algorithm";
    public static final long THOUSAND = 1000L;

    private OauthConstants() {
    }
}
