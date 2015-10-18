package com.power4j.oauth2.security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * ClassName: com.power4j.oauth2.rs.security <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-17
 */
public class OAuth2SubjectFactory extends DefaultWebSubjectFactory {


    @Override
    public Subject createSubject(SubjectContext context) {


        boolean authenticated = context.isAuthenticated();

        if (authenticated) {

            AuthenticationToken token = context.getAuthenticationToken();

            if (token != null && token instanceof OAuth2Token) {
                OAuth2Token oAuth2Token = (OAuth2Token) token;
                if (oAuth2Token.isRememberMe()) {
                    context.setAuthenticated(false);
                }
            }
        }

        return super.createSubject(context);
    }
}
