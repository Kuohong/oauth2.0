package com.power4j.oauth2.provider;

import com.power4j.oauth2.common.pojo.ClientDetails;

import java.util.List;

/**
 * ClassName: com.power4j.oauth2.provider.ClientRegistrationProvider <br>
 * ClientRegistrationProvider
 *
 * @author Kuo Hong
 * @version 2015-10-20
 */
public interface ClientRegistrationProvider {
    ClientDetails getClient(String clientId);
    void setClient(ClientDetails client);
    ClientDetails removeClient(String clientId);
    List<ClientDetails> getClients();
}
