package org.power4j.oauth2.dao;

import org.apache.ibatis.annotations.Param;
import org.power4j.oauth2.common.pojo.ClientDetails;
import org.power4j.oauth2.common.pojo.ClientStatus;

/**
 * ClassName: org.power4j.oauth2.dao.ClientDetailsDao <br>
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface ClientDetailsDao {
    ClientDetails findClientDetails(@Param("clientId") String clientId, @Param("status")ClientStatus status);

    int saveClientDetails(ClientDetails clientDetails);

}
