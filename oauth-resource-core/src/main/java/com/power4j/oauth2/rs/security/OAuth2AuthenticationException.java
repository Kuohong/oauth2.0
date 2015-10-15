package com.power4j.oauth2.rs.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * ClassName: com.power4j.oauth2.OAuth2AuthenticationException <br>
 *
 * @author Kuo Hong
 * @version 2015-10-14
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException() {
    }

    public OAuth2AuthenticationException(String message) {
        super(message);
    }

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }

    public OAuth2AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
