package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTk5OTIzODQwNywianRpIjoiNDQ2NDViMGUtMzdjZi00N2MxLWE0NDMtNjNkZGIzOWYzZGYyIiwiY2xpZW50X2lkIjoic3ppdGhlaW1hIiwidXNlcm5hbWUiOiJzeml0aGVpbWEifQ.YY1CXUYkzNc1fVjBwzcatqqUJlxLnFDcm7q5Q0yLsCo9NWMB-3NMfTa0QBJ3RfL17jw9eYtBkl9fHHUMzI4VDy4I2cIzTkYo-lC_J-6CLiH8-MPoPOXzN04gMS_Gxyh6rghtOhERkNfiiEodM2sOowq7211iMj3nb8N9eL3ggOCImuri2wbzZZh5SaqL66fRZqcnC4Bjta0YN0SxcOgqrWS6ZGy5GFwciVOZjWbFZE1-TxA8_eEtXLzkT9D-QkLdlBC5u0ETdHBVPf5CIFcCNUrtKg9V0waPeGOfgiryLn1rBWvi-RdDmLzaQMUmbl-sMOcT03Nsyp1sG6jawvaoIg";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi7Drp7TubteIxAM71vQfH1trXsobxVrCAdONO3Moh6e+St0pP1IcLXBS5QtwF3dCIeCp9h9Tug0WZ3NRPJxBOl+h23nKgfnBpbqjQRa4/pZty4T4R9pqeVQtXpyUD1SyDCfy8hqVbd5wX+3l8+zHgKf3DmpEvfRxh0eRXcRV/5luU6T7Cu+7fu0eTbQpKT7gwDFRNRwhDIe+1uLgzmn/9ZpwtM7f3aumN97wFltsTMFlVFCr/3UDJXRt8opm2Qm3Z+vDA4x7qFgW5dVmXU3nCp7pjBK1zRMDnemRjiizo3Ha1mR9SJBA6zYgt1ZV71kndOjn5pPnq9f3RIZIAMgDyQIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
