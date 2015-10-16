package com.power4j.oauth2.rs.security.rsfilter.pojo;

import com.power4j.oauth2.common.pojo.ClientDetails;
import org.apache.oltu.oauth2.rsfilter.OAuthClient;

/**
 * Wrapper ClientDetails
 * implement OAuthClient
 *
 */
public class RSOAuthClient implements OAuthClient {

    private ClientDetails clientDetails;

    public RSOAuthClient(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    @Override
    public String getClientId() {
        return clientDetails.getClientId();
    }

    public ClientDetails clientDetails() {
        return clientDetails;
    }
}

