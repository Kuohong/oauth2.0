package com.power4j.oauth2.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * ClassName: com.monkeyk.os.common.util.Base64UrlUtility <br>
 *
 * @author Kuo Hong
 * @version 2015-10-10
 */
public final class Base64UrlUtility {
    private Base64UrlUtility() {
    }

    public static byte[] decode(String encoded) throws Base64Exception {
        return Base64Utility.decode(encoded, true);
    }

    public static String encode(String str) {
        try {
            return encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String encode(byte[] id) {
        return encodeChunk(id, 0, id.length);
    }

    public static String encodeChunk(byte[] id, int offset, int length) {
        char[] chunk = Base64Utility.encodeChunk(id, offset, length, true);
        return chunk != null ? new String(chunk) : null;
    }

    public static void encodeAndStream(byte[] id, int o, int l, OutputStream os)
        throws IOException {
        Base64Utility.encodeAndStream(id, o, l, true, os);
    }
}
