package org.power4j.oauth2.support;

/**
 * ClassName: org.power4j.oauth2.support.AuthenticationIdGenerator <br>
 *
 * @author Kuo Hong
 */
public interface AuthenticationIdGenerator {
    String generate(String clientId, String username, String scope);
}
