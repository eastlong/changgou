package com.changgou.order.config;

import com.alibaba.fastjson.JSON;
import entity.CacheKey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.order.config *
 * @since 1.0
 */

@Component
public class TokenDecode {

    private static final String PUBLIC_KEY = "public.key";

    // 获取令牌
    public String getToken() {
        OAuth2AuthenticationDetails authentication = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        String tokenValue = authentication.getTokenValue();//ejebaldsfjalfjaljflajflajflajfla

        return tokenValue;
    }


    /**
     * 获取当前的登录的用户的用户信息
     *
     * @return
     */
    public Map<String, String> getUserInfo() {
        //1.获取令牌
        String token = getToken();

        //2.解析令牌  公钥
        String pubKey = getPubKey();

        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(pubKey));
        String claims = jwt.getClaims();//{}


        System.out.println(claims);
        //3.返回
        Map<String,String> map = JSON.parseObject(claims, Map.class);
        return map;
    }

    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }



}
