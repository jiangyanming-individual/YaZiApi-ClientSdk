package com.jiang.getname.client;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jiang.getname.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.jiang.getname.utils.SignUtils.getSign;


/**
 * 调用第三方接口的客户端:
 */
public class YaZiApiClient {

    private String accessKey;
    private String secretKey;

    public YaZiApiClient(String accessKey,String secretKey) {
        this.secretKey = secretKey;
        this.accessKey = accessKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result1= HttpUtil.get("http://localhost:8123/api/name/",paramMap);
        System.out.println(result1);
        return "get的方式得到name=>"+ result1;
    }

    public String getNameByPost(String name){

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result2= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result2);
        return "post的方式得到name=>"+ result2;
    }

    public String getNameByUser(User user){
        //user==> JSON
        String json = JSONUtil.toJsonStr(user);
        //请求对象:
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/userGetName")
                .addHeaders(getHeaderMap(json)) //添加请求头
                .body(json)
                .execute();
        //获取状态码:
        System.out.println(httpResponse.getStatus());
        //获取主要的内容
        System.out.println(httpResponse.body());
        return "user的方式得到name=>"+ httpResponse.body();
    }

    private Map<String,String> getHeaderMap(String body){
        Map<String,String> headerMap = new HashMap<String,String>();
        headerMap.put("accessKey",accessKey);
        headerMap.put("nonce", RandomUtil.randomNumbers(4));
        headerMap.put("body", body); //参数
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis() /1000));
        //生成摘要信息：
        headerMap.put("sign",getSign(body,secretKey)); //secretKey 一定不能发传到header中
        return headerMap;
    }


}
