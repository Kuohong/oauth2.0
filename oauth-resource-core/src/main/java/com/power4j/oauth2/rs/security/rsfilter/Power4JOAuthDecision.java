package com.power4j.oauth2.rs.security.rsfilter;

import com.power4j.oauth2.rs.security.rsfilter.pojo.RSOAuthClient;
import org.apache.oltu.oauth2.rsfilter.OAuthClient;
import org.apache.oltu.oauth2.rsfilter.OAuthDecision;

import java.security.Principal;

/**
 * ClassName: com.power4j.oauth2.resource.security.rsfilter.Power4JOAuthDecision <br>
 *
 * @author Kuo Hong
 * @version 2015-10-15
 */
public class Power4JOAuthDecision implements OAuthDecision {


    private boolean authorized;

    private Principal principal;

    private RSOAuthClient oAuthClient;

    public Power4JOAuthDecision() {
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public OAuthClient getOAuthClient() {
        return oAuthClient;
    }

    public Power4JOAuthDecision setPrincipal(Principal principal) {
        this.principal = principal;
        return this;
    }

    public Power4JOAuthDecision setOAuthClient(RSOAuthClient oAuthClient) {
        this.oAuthClient = oAuthClient;
        return this;
    }

    public Power4JOAuthDecision setAuthorized(boolean authorized) {
        this.authorized = authorized;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            "authorized=" + authorized +
            ", principal=" + principal +
            ", oAuthClient=" + oAuthClient +
            '}';
    }
}
