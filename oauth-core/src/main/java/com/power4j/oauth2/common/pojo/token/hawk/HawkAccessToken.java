package com.power4j.oauth2.common.pojo.token.hawk;


import com.power4j.oauth2.common.constants.OAuthConstants;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import com.power4j.oauth2.common.pojo.ClientDetails;
import com.power4j.oauth2.common.pojo.token.ServerAccessToken;
import com.power4j.oauth2.common.util.HmacUtils;

/**
 * ClassName: com.monkeyk.os.domain.oauth <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public class HawkAccessToken extends ServerAccessToken {
    public HawkAccessToken(ClientDetails client, HmacAlgorithm algo, String tokenKey, String macKey,
        long lifetime, long issuedAt) {
        super(checkClient(client), OAuthConstants.HAWK_TOKEN_TYPE, tokenKey, lifetime, issuedAt);
        this.setExtraParameters(algo, macKey);
    }

    private void setExtraParameters(HmacAlgorithm algo, String macKey) {
        String theKey = macKey == null ? HmacUtils.generateKey(algo.getJavaName()) : macKey;
        super.getParameters().put(OAuthConstants.HAWK_TOKEN_KEY, theKey);
        super.getParameters().put(OAuthConstants.HAWK_TOKEN_ALGORITHM, algo.getOAuthName());
    }

    private static ClientDetails checkClient(ClientDetails c) {
        if (!c.isConfidential()) {
            throw new OAuthRuntimeException("Public clients can not keep a MAC secret");
        }
        return c;
    }

    /**
     * Clone
     * Exclude token, refresh_token, authenticationId, expired
     *
     * @return New ServerAccessToken instance
     */
    @Override public ServerAccessToken cloneMe() {
        return null;
    }
}
