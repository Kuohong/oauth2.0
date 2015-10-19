package com.power4j.oauth2.service.impl;

import com.power4j.oauth2.support.AuthenticationIdGenerator;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: com.power4j.oauth2.service.impl <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public class AbstractOauthServiceImpl {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private AuthenticationIdGenerator authenticationIdGenerator;
    protected String currentUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

}
