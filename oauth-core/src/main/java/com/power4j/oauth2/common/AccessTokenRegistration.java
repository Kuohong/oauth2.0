package com.power4j.oauth2.common;

import com.power4j.oauth2.common.pojo.ClientDetails;

import java.util.LinkedList;
import java.util.List;

/**
 * ClassName: com.power4j.oauth2.common.AccessTokenRegistration <br>
 * AccessTokenRegistration
 *
 * @author Kuo Hong
 * @version 2015-10-20
 */
public class AccessTokenRegistration {
    private ClientDetails client;
    private List<String> requestedScope = new LinkedList<String>();
    private List<String> approvedScope = new LinkedList<String>();
    private String grantType;
    //private UserSubject subject;
    private String audience;

    /**
     * Sets the {@link ClientDetails} instance
     * @param client the client
     */
    public void setClient(ClientDetails client) {
        this.client = client;
    }

    /**
     * Returns the {@link ClientDetails} instance
     * @return the client.
     */
    public ClientDetails getClient() {
        return client;
    }

    /**
     * Sets the requested scope
     * @param requestedScope the scope
     */
    public void setRequestedScope(List<String> requestedScope) {
        this.requestedScope = requestedScope;
    }

    /**
     * Gets the requested scope
     * @return the scope
     */
    public List<String> getRequestedScope() {
        return requestedScope;
    }

    /**
     * Sets the scope explicitly approved by the end user
     * @param approvedScope the approved scope
     */
    public void setApprovedScope(List<String> approvedScope) {
        this.approvedScope = approvedScope;
    }

    /**
     * Gets the scope explicitly approved by the end user
     * @return the scope
     */
    public List<String> getApprovedScope() {
        return approvedScope;
    }

    /**
     * Sets the {@link UserSubject) instance capturing
     * the information about the end user
     * @param subject the end user subject
     */
    /*public void setSubject(UserSubject subject) {
        this.subject = subject;
    }*/

    /**
     * Gets the {@link UserSubject) instance capturing
     * the information about the end user
     * @return the subject
     */
    /*public UserSubject getSubject() {
        return subject;
    }*/

    /**
     * Sets the type of grant which is exchanged for this token
     * @param grantType the grant type
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
    /**
     * Gets the type of grant which is exchanged for this token
     * @return the grant type
     */
    public String getGrantType() {
        return grantType;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }
}
