package com.jiang.getname.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {

    /**
     * 摘要加密,使用密钥单项加密
     * @param secretKey
     * @return
     */
    public static String getSign(String body, String secretKey){
        String content=body+"."+secretKey;
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(content);
    }
}
