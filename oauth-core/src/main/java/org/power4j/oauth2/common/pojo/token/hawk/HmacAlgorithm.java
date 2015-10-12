package org.power4j.oauth2.common.pojo.token.hawk;

import com.monkeyk.os.domain.oauth.Constants;

/**
 * ClassName: com.monkeyk.os.domain.oauth.hawk <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public enum HmacAlgorithm {
    HmacSHA1(Constants.HMAC_ALGO_SHA_1),
    HmacSHA256(Constants.HMAC_ALGO_SHA_256);

    private final String oauthName;

    private HmacAlgorithm(String oauthName) {
        this.oauthName = oauthName;
    }

    public String getOAuthName() {
        return oauthName;
    }

    public String getJavaName() {
        return name();
    }

    public static HmacAlgorithm toHmacAlgorithm(String value) {
        if (Constants.HMAC_ALGO_SHA_1.equals(value)) {
            return HmacSHA1;
        }
        if (Constants.HMAC_ALGO_SHA_256.equals(value)) {
            return HmacSHA256;
        }
        return null;
    }
}
