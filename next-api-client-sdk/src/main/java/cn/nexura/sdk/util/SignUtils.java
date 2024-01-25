package cn.nexura.sdk.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.HashMap;

/**
 * @author PeiYP
 * @since 2024年01月23日 15:33
 */
public class SignUtils {

    public static String genSign(HashMap<String, String> map, String secretKey) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = map.toString() + "." + secretKey;
        return digester.digestHex(content);
    }

}
