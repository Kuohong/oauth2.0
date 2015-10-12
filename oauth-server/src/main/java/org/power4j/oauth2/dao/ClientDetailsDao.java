package org.power4j.oauth2.dao;

import org.power4j.oauth2.common.pojo.ClientDetails;

/**
 * ClassName: org.power4j.oauth2.dao.ClientDetailsDao <br>
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface ClientDetailsDao {
    ClientDetails findClientDetails(String clientId);

    int saveClientDetails(ClientDetails clientDetails);

}
