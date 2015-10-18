import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * ClassName: PACKAGE_NAME <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-17
 */
public class HashTest {
    private static final MessageDigest md;
    private static final BASE64Encoder b64Encoder;

    static{
        try {
            md = MessageDigest.getInstance("MD5", "SUN");
            b64Encoder = new BASE64Encoder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
       /* hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数*/

        HashRequest request = new HashRequest.Builder()
            .setAlgorithmName("SHA-512").setSource(ByteSource.Util.bytes("test"))
            .setSalt(ByteSource.Util.bytes("2")).setIterations(1).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
        /*String s = toPasswd("123456", "".getBytes("UTF-8"));
        System.out.println(printHexString());*/
        System.out.println(b64Encoder.encode(md.digest("123456".getBytes("UTF-8"))));
        System.out.println(printHexString(md.digest("123456".getBytes("UTF-8"))));
    }

    public  static String printHexString( byte[] b) {
        String a = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            a = a+hex;
        }

        return a;
    }
    /**
     * 将客户输入的密码加密
     * @param inputPasswd 客户输入的密码
     * @param salt 盐
     * @return 加密后的字符串
     */
    synchronized
    private static String toPasswd(String inputPasswd, byte[] salt){
        String pwd = "";

        try{
            md.reset();
            //md.update(salt);
            md.update(inputPasswd.getBytes("UTF-8"));
            byte[] bys = md.digest();;
            //1:AASLexNtFtI7e1IuQIg88ZNA==
            //879:AA/lCM5NEwVQJ25YYomE1ldQ==
            //1000:AARXKQat7z+/iu2w6KpKgLQA==
            //          for(int i=0; i<1000; i++){
            //              md.reset();
            //              bys = md.digest(bys);
            //          }

            pwd += (char)salt[0];
            pwd += (char)salt[1];
            pwd += b64Encoder.encode(bys);
        }catch(Exception ex){
            ex.printStackTrace();
            pwd = "";
        }

        return pwd;
    }
}
