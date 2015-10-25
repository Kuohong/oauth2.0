package com.power4j.oauth2.common;

/**
 * ClassName: org.power4j.common.constants.OAuthConstants <br>
 * OAuthConstants
 *
 * @author Kuo Hong
 * @version 2015-10-11
 */
public class OAuthConstants {
    //Default value
    public static long REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 10L; // default 30 days.
    //Default value
    public static long ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 2L; // default 12 hours.
    public static final String REQUEST_USERNAME = "username";
    public static final String REQUEST_PASSWORD = "password";

    public static final String REQUEST_USER_OAUTH_APPROVAL = "user_oauth_approval";

    public static final String OAUTH_LOGIN_VIEW = "oauth_login";
    public static final String OAUTH_APPROVAL_VIEW = "oauth_approval";

    // Well-known token types
    public static final String BEARER_TOKEN_TYPE = "bearer";
    public static final String HAWK_TOKEN_TYPE = "hawk";

    public static final String REFRESH_TOKEN_TYPE = "refresh";


    // Mac/Hawk HMAC algorithm names
    public static final String HMAC_ALGO_SHA_1 = "hmac-sha-1";
    public static final String HMAC_ALGO_SHA_256 = "hmac-sha-256";

    // Hawk token parameters
    // Set by Access Token Service
    public static final String HAWK_TOKEN_KEY = "secret";
    public static final String HAWK_TOKEN_ALGORITHM = "algorithm";
    public static final long THOUSAND = 1000L;


    // Error constants
    public static final String ERROR_KEY = "error";
    public static final String ERROR_DESCRIPTION_KEY = "error_description";
    public static final String ERROR_URI_KEY = "error_uri";

    public static final String SERVER_ERROR = "server_error";
    public static final String INVALID_REQUEST = "invalid_request";
    public static final String INVALID_GRANT = "invalid_grant";
    public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
    public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
    public static final String INVALID_CLIENT = "invalid_client";
    public static final String INVALID_SCOPE = "invalid_scope";
    public static final String ACCESS_DENIED = "access_denied";

    // Default refresh token scope value
    public static final String REFRESH_TOKEN_SCOPE = "refreshToken";

    public static final String OOB_RESPONSE = "um:ietf:wg:oauth:2.0:oob";

    private OAuthConstants() {
    }
}
