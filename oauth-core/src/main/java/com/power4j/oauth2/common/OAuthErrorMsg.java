package com.power4j.oauth2.common;

/**
 * ClassName: com.power4j.oauth2.common.OAuthErrorMsg <br>
 * Captures OAuth2 error properties
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public class OAuthErrorMsg {
    private String error;
    private String errorDescription;
    private String errorUri;
    private String state;

    public OAuthErrorMsg() {

    }

    public OAuthErrorMsg(String error) {
        this.error = error;
    }

    public OAuthErrorMsg(String error, String descr) {
        this.error = error;
        this.errorDescription = descr;
    }

    /**
     * Sets the error such as "invalid_grant", etc
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Gets the error
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error description
     * @param errorDescription error description
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * Gets the error description
     * @return error description
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the optional link to the page
     * describing the error in detail
     * @param errorUri error page URI
     */
    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }

    /**
     * Gets the optional link to the page
     * describing the error in detail
     * @return  errorUri error page URI
     */
    public String getErrorUri() {
        return errorUri;
    }

    /**
     * Sets the client state token which needs to be returned
     * to the client alongside the error information
     * if it was provided during the client request
     * @param state the client state token
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the client state token
     * @return the state
     */
    public String getState() {
        return state;
    }
}
