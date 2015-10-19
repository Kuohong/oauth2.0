package com.power4j.oauth2.exception;

import com.power4j.oauth2.common.constants.OAuthConstants;
import org.apache.oltu.oauth2.common.error.OAuthError;

/**
 * ClassName: com.power4j.oauth2.exception <br>
 * Encapsulates OAuth-related problems
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public class OAuthServiceException extends RuntimeException {

    private static final long serialVersionUID = 343738539234766320L;
    private OAuthError error;

    public OAuthServiceException() {
        super(OAuthConstants.SERVER_ERROR);
    }

    public OAuthServiceException(String message) {
        super(message);
    }

    public OAuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthServiceException(Throwable cause) {
        super(OAuthConstants.SERVER_ERROR, cause);
    }

    public OAuthServiceException(OAuthError error) {
        this.error = error;
    }

    public OAuthServiceException(OAuthError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public OAuthError getError() {
        return error;
    }

}
