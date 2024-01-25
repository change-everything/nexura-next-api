package cn.nexura.nextapiinterface.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.nexura.nextapiinterface.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PeiYP
 * @since 2024年01月23日 13:21
 */
@Data
@AllArgsConstructor
public class NextApiClient {

    private String accessKey;

    private String secretKey;

    public String getNameUsingGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:9178/name/", paramMap);
        System.out.println("result = " + result);
        return result;
    }

    public String getNameUsingPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post("http://localhost:9178/name/", paramMap);
        System.out.println("result = " + result);
        return result;
    }

    public String getNameBody(User user) {
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post("http://localhost:9178/name/json/")
                .body(json)
                .addHeaders(getHeaderMap(json))
                .execute()
                .body();
        System.out.println("result = " + result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("body", body);
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("sign", genSign(map, secretKey));
        return map;
    }

    private String genSign(HashMap<String, String> map, String secretKey) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = map.toString() + "." + secretKey;
        return digester.digestHex(content);
    }

}
