package cn.nexura.sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.nexura.common.model.entity.User;
import cn.nexura.sdk.util.SignUtils;
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

    public static final String GATEWAY_URL = "http://openapi.nexuracloud.cn/api";

    public String getNameUsingGet(String userRequestParams) {

        User user = JSONUtil.toBean(userRequestParams, User.class);
        String result = HttpRequest.get(GATEWAY_URL + "/name/")
                .body("name="+ user.getUserName())
                .addHeaders(getHeaderMap(user.getUserName()))
                .execute()
                .body();
        System.out.println("result = " + result);
        return result;
    }

    public String getNameUsingPost(String userRequestParams) {
        User user = JSONUtil.toBean(userRequestParams, User.class);
        String result = HttpRequest.post(GATEWAY_URL + "/name/")
                .body("name="+ user.getUserName())
                .addHeaders(getHeaderMap(user.getUserName()))
                .execute()
                .body();;
        System.out.println("result = " + result);
        return result;
    }

    public String getNameBody(String userRequestParams) {
        User user = JSONUtil.toBean(userRequestParams, User.class);
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post(GATEWAY_URL + "/name/json/")
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
        map.put("sign", SignUtils.genSign(body, secretKey));
        return map;
    }



}
