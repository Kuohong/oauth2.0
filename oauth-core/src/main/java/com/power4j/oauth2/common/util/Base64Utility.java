//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.power4j.oauth2.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public final class Base64Utility {
    private static final Logger LOG = LoggerFactory.getLogger(Base64Utility.class);
    private static final char[] BCS =
        new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final char[] BCS_URL_SAFE;
    private static final char PAD = '=';
    private static final int BDTSIZE = 128;
    private static final byte[] BDT;
    private static final int PAD_SIZE0 = 1;
    private static final int PAD_SIZE4 = 2;
    private static final int PAD_SIZE8 = 3;
    private static final Charset CHARSET_UTF8;

    private Base64Utility() {
    }

    public static byte[] decodeChunk(char[] id, int o, int l) throws Base64Exception {
        if (l - o < 4) {
            return null;
        } else {
            char[] ib = new char[4];
            int ibcount = 0;
            int octetCount = 3 * (l / 4);
            if (id[l - 1] == 61) {
                octetCount -= id[l - 2] == 61 ? 2 : 1;
            }

            byte[] ob = new byte[octetCount];
            int obcount = 0;

            for (int tmp = o; tmp < o + l && tmp < id.length; ++tmp) {
                if (id[tmp] == 61 || id[tmp] < BDT.length && BDT[id[tmp]] != 127) {
                    ib[ibcount++] = id[tmp];
                    if (ibcount == ib.length) {
                        ibcount = 0;
                        obcount += processEncodeme(ib, ob, obcount);
                    }
                }
            }

            if (obcount != ob.length) {
                byte[] var9 = new byte[obcount];
                System.arraycopy(ob, 0, var9, 0, obcount);
                ob = var9;
            }

            return ob;
        }
    }

    public static byte[] decode(String id) throws Base64Exception {
        return decode(id, false);
    }

    public static byte[] decode(String id, boolean urlSafe) throws Base64Exception {
        if (urlSafe) {
            id = id.replace("-", "+").replace('_', '/');
            switch (id.length() % 4) {
                case 0:
                    break;
                case 1:
                default:
                    throw new Base64Exception("BASE64_RUNTIME_EXCEPTION");
                case 2:
                    id = id + "==";
                    break;
                case 3:
                    id = id + "=";
            }
        }

        try {
            char[] e = id.toCharArray();
            return decodeChunk(e, 0, e.length);
        } catch (Exception var3) {
            LOG.warn("Invalid base64 encoded string : " + id);
            throw new Base64Exception("BASE64_RUNTIME_EXCEPTION");
        }
    }

    public static void decode(char[] id, int o, int l, OutputStream ostream)
        throws Base64Exception {
        try {
            ostream.write(decodeChunk(id, o, l));
        } catch (Exception var5) {
            LOG.warn("Invalid base64 encoded string : " + new String(id));
            throw new Base64Exception("BASE64_RUNTIME_EXCEPTION");
        }
    }

    public static void decode(String id, OutputStream ostream) throws Base64Exception {
        try {
            char[] e = id.toCharArray();
            ostream.write(decodeChunk(e, 0, e.length));
        } catch (IOException var3) {
            throw new Base64Exception("BASE64_RUNTIME_EXCEPTION");
        } catch (Exception var4) {
            LOG.warn("Invalid base64 encoded string : " + id);
            throw new Base64Exception("BASE64_RUNTIME_EXCEPTION");
        }
    }

    public static String encode(byte[] id) {
        return encode(id, false);
    }

    public static String encode(byte[] id, boolean urlSafe) {
        char[] cd = encodeChunk(id, 0, id.length);
        return new String(cd, 0, cd.length);
    }

    public static char[] encodeChunk(byte[] id, int o, int l) {
        return encodeChunk(id, o, l, false);
    }

    public static char[] encodeChunk(byte[] id, int o, int l, boolean urlSafe) {
        if (l <= 0) {
            return null;
        } else {
            char[] out;
            int rindex;
            if (l % 3 == 0) {
                out = new char[l / 3 * 4];
            } else {
                rindex = !urlSafe ? 4 : (l % 3 == 1 ? 2 : 3);
                out = new char[l / 3 * 4 + rindex];
            }

            rindex = o;
            int windex = 0;
            int rest = l;

            char[] base64Table;
            int i;
            for (base64Table = urlSafe ? BCS_URL_SAFE : BCS; rest >= 3; rest -= 3) {
                i = ((id[rindex] & 255) << 16) + ((id[rindex + 1] & 255) << 8) + (id[rindex + 2]
                    & 255);
                out[windex++] = base64Table[i >> 18];
                out[windex++] = base64Table[i >> 12 & 63];
                out[windex++] = base64Table[i >> 6 & 63];
                out[windex++] = base64Table[i & 63];
                rindex += 3;
            }

            if (rest == 1) {
                i = id[rindex] & 255;
                out[windex++] = base64Table[i >> 2];
                out[windex++] = base64Table[i << 4 & 63];
                if (!urlSafe) {
                    out[windex++] = 61;
                    out[windex++] = 61;
                }
            } else if (rest == 2) {
                i = ((id[rindex] & 255) << 8) + (id[rindex + 1] & 255);
                out[windex++] = base64Table[i >> 10];
                out[windex++] = base64Table[i >> 4 & 63];
                out[windex++] = base64Table[i << 2 & 63];
                if (!urlSafe) {
                    out[windex++] = 61;
                }
            }

            return out;
        }
    }

    public static void encodeAndStream(byte[] id, int o, int l, OutputStream os)
        throws IOException {
        encodeAndStream(id, o, l, false, os);
    }

    public static void encodeAndStream(byte[] id, int o, int l, boolean urlSafe, OutputStream os)
        throws IOException {
        if (l > 0) {
            int rindex = o;
            int rest = l;
            char[] base64Table = urlSafe ? BCS_URL_SAFE : BCS;

            char[] chunk;
            int finalLenToWrite;
            for (chunk = new char[4]; rest >= 3; rest -= 3) {
                finalLenToWrite =
                    ((id[rindex] & 255) << 16) + ((id[rindex + 1] & 255) << 8) + (id[rindex + 2]
                        & 255);
                chunk[0] = base64Table[finalLenToWrite >> 18];
                chunk[1] = base64Table[finalLenToWrite >> 12 & 63];
                chunk[2] = base64Table[finalLenToWrite >> 6 & 63];
                chunk[3] = base64Table[finalLenToWrite & 63];
                writeCharArrayToStream(chunk, 4, os);
                rindex += 3;
            }

            if (rest != 0) {
                if (rest == 1) {
                    finalLenToWrite = id[rindex] & 255;
                    chunk[0] = base64Table[finalLenToWrite >> 2];
                    chunk[1] = base64Table[finalLenToWrite << 4 & 63];
                    if (!urlSafe) {
                        chunk[2] = 61;
                        chunk[3] = 61;
                    }
                } else if (rest == 2) {
                    finalLenToWrite = ((id[rindex] & 255) << 8) + (id[rindex + 1] & 255);
                    chunk[0] = base64Table[finalLenToWrite >> 10];
                    chunk[1] = base64Table[finalLenToWrite >> 4 & 63];
                    chunk[2] = base64Table[finalLenToWrite << 2 & 63];
                    if (!urlSafe) {
                        chunk[3] = 61;
                    }
                }

                finalLenToWrite = !urlSafe ? 4 : (rest == 1 ? 2 : 3);
                writeCharArrayToStream(chunk, finalLenToWrite, os);
            }
        }
    }

    private static void writeCharArrayToStream(char[] chunk, int len, OutputStream os)
        throws IOException {
        byte[] bytes = CHARSET_UTF8.encode(CharBuffer.wrap(chunk, 0, len)).array();
        os.write(bytes);
    }

    public static void encodeChunk(byte[] id, int o, int l, OutputStream ostream)
        throws Base64Exception {
        try {
            ostream.write((new String(encodeChunk(id, o, l))).getBytes());
        } catch (IOException var5) {
            throw new Base64Exception("BASE64_RUNTIME_EXCEPTION");
        }
    }

    public static void encode(byte[] id, int o, int l, Writer writer) throws Base64Exception {
        try {
            writer.write(encodeChunk(id, o, l));
        } catch (IOException var5) {
            throw new Base64Exception("BASE64_ENCODE_WRITER_IOEXCEPTION");
        }
    }

    private static int processEncodeme(char[] ib, byte[] ob, int p) throws Base64Exception {
        byte spad = 3;
        if (ib[3] == 61) {
            spad = 2;
        }

        if (ib[2] == 61) {
            spad = 1;
        }

        byte b0 = BDT[ib[0]];
        byte b1 = BDT[ib[1]];
        byte b2 = BDT[ib[2]];
        byte b3 = BDT[ib[3]];
        switch (spad) {
            case 1:
                ob[p] = (byte) (b0 << 2 & 252 | b1 >> 4 & 3);
                return 1;
            case 2:
                ob[p++] = (byte) (b0 << 2 & 252 | b1 >> 4 & 3);
                ob[p] = (byte) (b1 << 4 & 240 | b2 >> 2 & 15);
                return 2;
            case 3:
                ob[p++] = (byte) (b0 << 2 & 252 | b1 >> 4 & 3);
                ob[p++] = (byte) (b1 << 4 & 240 | b2 >> 2 & 15);
                ob[p] = (byte) (b2 << 6 & 192 | b3 & 63);
                return 3;
            default:
                throw new IllegalStateException();
        }
    }

    public static boolean isValidBase64(int ch) {
        return ch == 61 || BDT[ch] != 127;
    }

    static {
        BCS_URL_SAFE = Arrays.copyOf(BCS, BCS.length);
        BDT = new byte[128];
        CHARSET_UTF8 = Charset.forName("UTF-8");

        int i;
        for (i = 0; i < 128; ++i) {
            BDT[i] = 127;
        }

        for (i = 0; i < BCS.length; ++i) {
            BDT[BCS[i]] = (byte) i;
        }

        BCS_URL_SAFE[62] = 45;
        BCS_URL_SAFE[63] = 95;
    }
}
