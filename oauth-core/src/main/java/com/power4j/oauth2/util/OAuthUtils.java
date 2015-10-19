package com.power4j.oauth2.util;

import com.power4j.oauth2.common.constants.OAuthConstants;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.exception.OAuthServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * ClassName: com.power4j.oauth2.util <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-19
 */
public class OAuthUtils {
    public static long getIssuedAt() {
        return System.currentTimeMillis() / OAuthConstants.THOUSAND;
    }

    public static boolean isExpired(Long issuedAt, Long lifetime) {
        return lifetime != -1
            && issuedAt + lifetime < System.currentTimeMillis() / 1000;
    }
    public static List<String> parseScope(String requestedScope) {
        List<String> list = new LinkedList<String>();
        if (requestedScope != null) {
            String[] scopeValues = requestedScope.split(" ");
            for (String scope : scopeValues) {
                if (!StringUtils.isEmpty(scope)) {
                    list.add(scope);
                }
            }
        }
        return list;
    }

    public static List<String> getRequestedScopes(ClientDetails client, String scopeParameter,
        boolean partialMatchScopeValidation) {
        List<String> requestScopes = parseScope(scopeParameter);
        List<String> registeredScopes = client.getRegisteredScopes();
        if (requestScopes.isEmpty()) {
            requestScopes.addAll(registeredScopes);
            return requestScopes;
        }
        if (!validateScopes(requestScopes, registeredScopes, partialMatchScopeValidation)) {
            throw new OAuthServiceException("Unexpected scope");
        }
        return requestScopes;
    }

    public static boolean validateScopes(List<String> requestScopes, List<String> registeredScopes,
        boolean partialMatchScopeValidation) {
        if (!registeredScopes.isEmpty()) {
            // if it is a strict validation then pre-registered scopes have to contains all
            // the current request scopes
            if (!partialMatchScopeValidation) {
                return registeredScopes.containsAll(requestScopes);
            } else {
                for (String requestScope : requestScopes) {
                    boolean match = false;
                    for (String registeredScope : registeredScopes) {
                        if (requestScope.startsWith(registeredScope)) {
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
