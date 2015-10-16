package com.power4j.oauth2.dao;

import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.CommonStatus;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: org.power4j.oauth2.dao.ClientDetailsDao <br>
 *
 * @author Kuo Hong
 * @version 2015-10-12
 */
public interface ClientDetailsDao {
    ClientDetails findClientDetails(@Param("clientId") String clientId, @Param("status")CommonStatus status);

    int saveClientDetails(ClientDetails clientDetails);

}
