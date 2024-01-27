package cn.nexura.sdk.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.HashMap;

/**
 * @author PeiYP
 * @since 2024年01月23日 15:33
 */
public class SignUtils {

    public static String genSign(String body, String secretKey) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return digester.digestHex(content);
    }

    public static void main(String[] args) {
        String s = genSign("{\"userName\":\"nexura\"}", "123456789");
        String s1 = genSign("{\"userName\":\"nexura\"}", "123456789");

        System.out.println("s = " + s);
        System.out.println("s1 = " + s1);
    }

}
