package com.power4j.oauth2.service.impl;

import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.dao.ClientDetailsDao;
import com.power4j.oauth2.dao.ServerAccessTokenDao;
import com.power4j.oauth2.service.OAuthRSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: com.power4j.oauth2.rs.service.impl.OAuthRSServiceImpl <br>
 *
 * @author Kuo Hong
 * @version 2015-10-15
 */
@Service("oAuthRSService")
public class OAuthRSServiceImpl implements OAuthRSService {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthRSServiceImpl.class);


    @Resource
    private ServerAccessTokenDao serverAccessTokenDao;
    @Resource
    private ClientDetailsDao clientDetailsDao;


    @Override
    public ServerAccessToken loadAccessTokenByTokenId(String tokenId) {
       return serverAccessTokenDao.selectAccessTokenByTokenId(tokenId);
    }

    @Override
    public ClientDetails loadClientDetails(String clientId, String resourceIds) {
        LOG.debug("Load ClientDetails by clientId: {}, resourceIds: {}", clientId, resourceIds);
        return clientDetailsDao.selectClientDetailsByClientIdAndResourceIds(clientId, resourceIds);
    }


}
