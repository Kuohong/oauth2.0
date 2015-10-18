package com.power4j.oauth2.security.rsfilter;

import org.apache.oltu.oauth2.rsfilter.OAuthClient;
import com.power4j.oauth2.common.pojo.ClientDetails;

/**
 * ClassName: com.power4j.oauth2.security.rsfilter.RSOAuthClient <br>
 *
 * @author Kuo Hong
 * @version 2015-10-15
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
