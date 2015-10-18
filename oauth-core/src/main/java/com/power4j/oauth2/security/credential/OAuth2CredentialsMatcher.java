package com.power4j.oauth2.security.credential;

import com.power4j.oauth2.security.OAuth2Token;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: com.power4j.oauth2.security.credential.OAuth2CredentialsMatcher <br>
 *
 * @author Kuo Hong
 * @version 2015-10-18
 */
public class OAuth2CredentialsMatcher implements CredentialsMatcher {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2CredentialsMatcher.class);

    // authz module
    private CredentialsMatcher authzCredentialsMatcher;
    //resources module
    private CredentialsMatcher resourcesCredentialsMatcher;

    public OAuth2CredentialsMatcher() {
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        LOG.debug("Do credentials match, token: {}, info: {}", token, info);

        if (token instanceof OAuth2Token) {
            LOG.debug("Call [resources] CredentialsMatcher: {}", resourcesCredentialsMatcher);
            return resourcesCredentialsMatcher.doCredentialsMatch(token, info);
        } else {
            LOG.debug("Call [authz] CredentialsMatcher: {}", authzCredentialsMatcher);
            return authzCredentialsMatcher.doCredentialsMatch(token, info);
        }

    }


    public void setAuthzCredentialsMatcher(CredentialsMatcher authzCredentialsMatcher) {
        this.authzCredentialsMatcher = authzCredentialsMatcher;
    }

    public void setResourcesCredentialsMatcher(CredentialsMatcher resourcesCredentialsMatcher) {
        this.resourcesCredentialsMatcher = resourcesCredentialsMatcher;
    }
}
