package com.changgou.test;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.test *
 * @since 1.0
 */
public class TestJWT {

    @Test
    public void createJwt(){
        long currentTimeMillis = System.currentTimeMillis();
        long l = currentTimeMillis + 20000;
        //生成令牌
        //创建jwtbuilder
        JwtBuilder builder = Jwts.builder();
        //1.头(不设置,默认) 2 载荷(数据) 3. 签名
        builder.setId("唯一的标识")
                .setIssuer("颁发者")//生成令牌的一方
                .setSubject("主题")//就是描述 jwt的信息
                .setExpiration(new Date(l))//设置过期时间
                .signWith(SignatureAlgorithm.HS256,"itcast");//设置签名



        //3.可以自定义载荷
        Map<String, Object> map = new HashMap<>();
        map.put("myaddress","cn");
        map.put("mycity","sz");
        builder.addClaims(map);


        //生成令牌
        String compact = builder.compact();
        System.out.println(compact);

    }

    @Test
    public void parseJwt(){
        //String  st = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgifQ.AU33UoJ8Vz_ZoCtKcvCEm5R0UFknLE-06E49z1h0nfI";
        //String  st = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgiLCJteWNpdHkiOiJzeiIsIm15YWRkcmVzcyI6ImNuIn0.VbuggDeIH66QlRAWGxoWPIKaRkCZOy45G-hAyz0Nq_k";
        String  st = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgiLCJleHAiOjE1NjY5ODU0NTMsIm15Y2l0eSI6InN6IiwibXlhZGRyZXNzIjoiY24ifQ.KtmMC-Hu1qlmYQgmLphoITQSZWRVv8YDssLZURKyczY";
        //解析令牌
        Jws<Claims> itcast = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws(st);
        System.out.println(itcast.getBody());


    }
}
