package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.IR9Qu9ZqYZ2gU2qgAziyT38UhEeL4Oi69ko-dzC_P9-Vjz40hwZDqxl8wZ-W2WAw1eWGIHV1EYDjg0-eilogJZ5UikyWw1bewXCpvlM-ZRtYQQqHFTlfDiVcFetyTayaskwa-x_BVS4pTWAskiaIKbKR4KcME2E5o1rEek-3YPkqAiZ6WP1UOmpaCJDaaFSdninqG0gzSCuGvLuG40x0Ngpfk7mPOecsIi5cbJElpdYUsCr9oXc53ROyfvYpHjzV7c2D5eIZu3leUPXRvvVAPJFEcSBiisxUSEeiGpmuQhaFZd1g-yJ1WQrixFvehMeLX2XU6W1nlL5ARTpQf_Jjiw";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
