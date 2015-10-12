package org.power4j.oauth2.common.util;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

/**
 * ClassName: com.monkeyk.os.common.util <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public final class HmacUtils {
    private HmacUtils() {

    }

    public static String encodeHmacString(String macSecret, String macAlgoJavaName, String data) {
        return Base64Utility.encode(computeHmac(macSecret, macAlgoJavaName, data));
    }

    public static String encodeHmacString(String macSecret, String macAlgoJavaName, String data,
        boolean urlSafe) {
        byte[] bytes = computeHmac(macSecret, macAlgoJavaName, data);
        return urlSafe ? Base64UrlUtility.encode(bytes) : Base64Utility.encode(bytes);
    }

    public static Mac getMac(String macAlgoJavaName) {
        return getMac(macAlgoJavaName, (String) null);
    }

    public static Mac getMac(String macAlgoJavaName, String provider) {
        try {
            return provider == null ?
                Mac.getInstance(macAlgoJavaName) :
                Mac.getInstance(macAlgoJavaName, provider);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (NoSuchProviderException e) {
            throw new SecurityException(e);
        }
    }

    public static Mac getMac(String macAlgoJavaName, Provider provider) {
        try {
            return Mac.getInstance(macAlgoJavaName, provider);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] computeHmac(String key, String macAlgoJavaName, String data) {
        Mac mac = getMac(macAlgoJavaName);
        return computeHmac(key, mac, data);
    }

    public static byte[] computeHmac(byte[] key, String macAlgoJavaName, String data) {
        return computeHmac(key, macAlgoJavaName, null, data);
    }

    public static byte[] computeHmac(byte[] key, String macAlgoJavaName,
        AlgorithmParameterSpec spec, String data) {
        Mac mac = getMac(macAlgoJavaName);
        return computeHmac(new SecretKeySpec(key, mac.getAlgorithm()), mac, spec, data);
    }

    public static byte[] computeHmac(String key, Mac hmac, String data) {
        try {
            return computeHmac(key.getBytes("UTF-8"), hmac, data);
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e);
        }
    }

    public static byte[] computeHmac(byte[] key, Mac hmac, String data) {
        SecretKeySpec secretKey = new SecretKeySpec(key, hmac.getAlgorithm());
        return computeHmac(secretKey, hmac, data);
    }

    public static byte[] computeHmac(Key secretKey, Mac hmac, String data) {
        return computeHmac(secretKey, hmac, null, data);
    }

    public static byte[] computeHmac(Key secretKey, Mac hmac, AlgorithmParameterSpec spec,
        String data) {
        initMac(hmac, secretKey, spec);
        return hmac.doFinal(data.getBytes());
    }

    public static Mac getInitializedMac(byte[] key, String algo, AlgorithmParameterSpec spec) {
        Mac hmac = getMac(algo);
        initMac(hmac, key, spec);
        return hmac;
    }

    private static void initMac(Mac hmac, byte[] key, AlgorithmParameterSpec spec) {
        initMac(hmac, new SecretKeySpec(key, hmac.getAlgorithm()), spec);

    }

    private static void initMac(Mac hmac, Key secretKey, AlgorithmParameterSpec spec) {
        try {
            if (spec == null) {
                hmac.init(secretKey);
            } else {
                hmac.init(secretKey, spec);
            }
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException(e);
        }
    }

    public static String generateKey(String algo) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algo);
            return Base64Utility.encode(keyGen.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        }
    }
}
