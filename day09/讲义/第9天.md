# 第9章 Spring Security Oauth2 JWT

## 学习目标

- 用户认证分析

- 认证技术方案了解

- ==SpringSecurity Oauth2.0入门==

  ```
  oauth2.0认证模式
  	授权码授权模式
  	密码授权模式
  授权流程
  ```

- ==用户授权认证开发==



## 1 用户认证分析

![1558173244406](images\1558173244406.png)

上面流程图描述了用户要操作的各个微服务，用户查看个人信息需要访问客户微服务，下单需要访问订单微服务，秒杀抢购商品需要访问秒杀微服务。每个服务都需要认证用户的身份，身份认证成功后，需要识别用户的角色然后授权访问对应的功能。



### 1.1 认证与授权

**身份认证**

用户身份认证即用户去访问系统资源时系统要求验证用户的身份信息，身份合法方可继续访问。常见的用户身份认证表现形式有：用户名密码登录，指纹打卡等方式。说通俗点，就相当于校验用户账号密码是否正确。

**用户授权**

用户认证通过后去访问系统的资源，系统会判断用户是否拥有访问资源的权限，只允许访问有权限的系统资源，没有权限的资源将无法访问，这个过程叫用户授权。



### 1.2 单点登录

![1558173244406](images\1558173244406.png)

用户访问的项目中，至少有3个微服务需要识别用户身份，如果用户访问每个微服务都登录一次就太麻烦了，为了提高用户的体验，我们需要实现让用户在一个系统中登录，其他任意受信任的系统都可以访问，这个功能就叫单点登录。

单点登录（Single Sign On），简称为 SSO，是目前比较流行的企业业务整合的解决方案之一。 SSO的定义是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统    



### 1.3 第三方账号登录

#### 1.3.1 第三方登录介绍

随着国内及国外巨头们的平台开放战略以及移动互联网的发展，第三方登录已经不是一个陌生的产品设计概念了。 所谓的第三方登录，是说基于用户在第三方平台上已有的账号和密码来快速完成己方应用的登录或者注册的功能。而这里的第三方平台，一般是已经拥有大量用户的平台，国外的比如Facebook，Twitter等，国内的比如微博、微信、QQ等。 

![1558174029075](images\1558174029075.png)

#### 1.3.2 第三方登录优点

```
1.相比于本地注册，第三方登录一般来说比较方便、快捷，能够显著降低用户的注册和登录成本，方便用户实现快捷登录或注册。
2.不用费尽心思地应付本地注册对账户名和密码的各种限制，如果不考虑昵称的重复性要求，几乎可以直接一个账号走遍天下，再也不用在大脑或者什么地方记住N多不同的网站或App的账号和密码，整个世界一下子清静了。
3.在第一次绑定成功之后，之后用户便可以实现一键登录，使得后续的登录操作比起应用内的登录来容易了很多。
4.对于某些喜欢社交，并希望将更多自己的生活内容展示给朋友的人来说，第三方登录可以实现把用户在应用内的活动同步到第三方平台上，省去了用户手动发布动态的麻烦。但对于某些比较注重个人隐私的用户来说，则会有一些担忧，所以龙哥所说的这个优点是有前提的。
5.因为降低了用户的注册或登录成本，从而减少由于本地注册的繁琐性而带来的隐形用户流失，最终提高注册转化率。
6.对于某些应用来说，使用第三方登录完全可以满足自己的需要，因此不必要设计和开发一套自己的账户体系。
7.通过授权，可以通过在第三方平台上分享用户在应用内的活动在第三方平台上宣传自己，从而增加产品知名度。
8.通过授权，可以获得该用户在第三方平台上的好友或粉丝等社交信息，从而后续可以针对用户的社交关系网进行有目的性的营销宣传，为产品的市场推广提供另一种渠道。
```



#### 1.3.3 第三方认证

当需要访问第三方系统的资源时需要首先通过第三方系统的认证（例如：微信认证），由第三方系统对用户认证通过，并授权资源的访问权限。

![1558174826405](images\1558174826405.png)



## 2 认证技术方案

### 2.1 单点登录技术方案

分布式系统要实现单点登录，通常将认证系统独立抽取出来，并且将用户身份信息存储在单独的存储介质，比如： MySQL、Redis，考虑性能要求，通常存储在Redis中，如下图：

![1558175040643](images\1558175040643.png)

单点登录的特点是： 

```
1、认证系统为独立的系统。 
2、各子系统通过Http或其它协议与认证系统通信，完成用户认证。 
3、用户身份信息存储在Redis集群。
```

 Java中有很多用户认证的框架都可以实现单点登录：

```
 1、Apache Shiro. 
 2、CAS 
 3、Spring security CAS    
```



### 2.2 Oauth2认证

OAuth（开放授权）是一个开放标准，允许用户授权第三方移动应用访问他们存储在另外的服务提供者上的信息，而不需要将用户名和密码提供给第三方移动应用或分享他们数据的所有内容，OAuth2.0是OAuth协议的延续版本。



#### 2.2.1 Oauth2认证流程

第三方认证技术方案最主要是解决认证协议的通用标准 问题，因为要实现 跨系统认证，各系统之间要遵循一定的接口协议。
OAUTH协议为用户资源的授权提供了一个安全的、开放而又简易的标准。同时，任何第三方都可以使用OAUTH认证服务，任何服务提供商都可以实现自身的OAUTH认证服务，因而OAUTH是开放的。业界提供了OAUTH的多种实现如PHP、JavaScript，Java，Ruby等各种语言开发包，大大节约了程序员的时间，因而OAUTH是简易的。互联网很多服务如Open API，很多大公司如Google，Yahoo，Microsoft等都提供了OAUTH认证服务，这些都足以说明OAUTH标准逐渐成为开放资源授权的标准。
Oauth协议目前发展到2.0版本，1.0版本过于复杂，2.0版本已得到广泛应用。
参考：https://baike.baidu.com/item/oAuth/7153134?fr=aladdin
Oauth协议：https://tools.ietf.org/html/rfc6749
下边分析一个Oauth2认证的例子，黑马程序员网站使用微信认证的过程：

![1562394786067](images\1562394786067.png)





1.客户端请求第三方授权

用户进入黑马程序的登录页面，点击微信的图标以微信账号登录系统，用户是自己在微信里信息的资源拥有者。

![1558175450281](images\1558175450281.png)

点击“用QQ账号登录”出现一个二维码，此时用户扫描二维码，开始给黑马程序员授权。    

![1558175557482](images\1558175557482.png)



2.资源拥有者同意给客户端授权

资源拥有者扫描二维码表示资源拥有者同意给客户端授权，微信会对资源拥有者的身份进行验证， 验证通过后，QQ会询问用户是否给授权黑马程序员访问自己的QQ数据，用户点击“确认登录”表示同意授权，QQ认证服务器会 颁发一个授权码，并重定向到黑马程序员的网站。

![1558175838653](images\1558175838653.png)    

3.客户端获取到授权码，请求认证服务器申请令牌 此过程用户看不到，客户端应用程序请求认证服务器，请求携带授权码。 

4.认证服务器向客户端响应令牌 认证服务器验证了客户端请求的授权码，如果合法则给客户端颁发令牌，令牌是客户端访问资源的通行证。 此交互过程用户看不到，当客户端拿到令牌后，用户在黑马程序员看到已经登录成功。 

5.客户端请求资源服务器的资源 客户端携带令牌访问资源服务器的资源。 黑马程序员网站携带令牌请求访问微信服务器获取用户的基本信息。 

6.资源服务器返回受保护资源 资源服务器校验令牌的合法性，如果合法则向用户响应资源信息内容。 注意：资源服务器和认证服务器可以是一个服务也可以分开的服务，如果是分开的服务资源服务器通常要请求认证 服务器来校验令牌的合法性。    



Oauth2.0认证流程如下： 引自Oauth2.0协议rfc6749 https://tools.ietf.org/html/rfc6749    

![1558175981680](images\1558175981680.png)

Oauth2包括以下角色： 

1、客户端 本身不存储资源，需要通过资源拥有者的授权去请求资源服务器的资源，比如：畅购在线Android客户端、畅购在 线Web客户端（浏览器端）、微信客户端等。 

2、资源拥有者 通常为用户，也可以是应用程序，即该资源的拥有者。 

3、授权服务器（也称认证服务器） 用来对资源拥有的身份进行认证、对访问资源进行授权。客户端要想访问资源需要通过认证服务器由资源拥有者授 权后方可访问。 

4、资源服务器 存储资源的服务器，比如，畅购网用户管理服务器存储了畅购网的用户信息等。客户端最终访问资源服务器获取资源信息。    



#### 2.2.2 Oauth2在项目的应用

Oauth2是一个标准的开放的授权协议，应用程序可以根据自己的要求去使用Oauth2，本项目使用Oauth2实现如 下目标：

1、畅购访问第三方系统的资源 

2、外部系统访问畅购的资源 

3、畅购前端（客户端） 访问畅购微服务的资源。 

4、畅购微服务之间访问资源，例如：微服务A访问微服务B的资源，B访问A的资源。



### 2.3 Spring security Oauth2认证解决方案 

本项目采用 Spring security + Oauth2完成用户认证及用户授权，Spring security 是一个强大的和高度可定制的身份验证和访问控制框架，Spring security 框架集成了Oauth2协议，下图是项目认证架构图：    

![1558176649652](images\1558176649652.png)

1、用户请求认证服务完成认证。 

2、认证服务下发用户身份令牌，拥有身份令牌表示身份合法。 

3、用户携带令牌请求资源服务，请求资源服务必先经过网关。 

4、网关校验用户身份令牌的合法，不合法表示用户没有登录，如果合法则放行继续访问。 

5、资源服务获取令牌，根据令牌完成授权。 

6、资源服务完成授权则响应资源信息。





## 3 Security Oauth2.0入门

### 3.1 学习知识点说明

本项目认证服务基于Spring Security Oauth2进行构建，并在其基础上作了一些扩展，采用JWT令牌机制，并自定 义了用户身份信息的内容。 本教程的主要目标是学习在项目中集成Spring Security Oauth2的方法和流程，通过 spring Security Oauth2的研究需要达到以下目标： 

1、理解Oauth2的授权码认证流程及密码认证的流程。 

2、理解spring Security Oauth2的工作流程。 

3、掌握资源服务集成spring Security框架完成Oauth2认证的流程。



### 3.2 搭建认证服务器

关于oauth2.0服务搭建的详细流程，如果有兴趣可以参考课件中提供的oauth2.o搭建手册完成搭建。



#### 3.2.1 导入认证工程

将课件中`day09\oauth2.0\changgou-user-oauth`的工程导入到项目中去，如下图：

![1562408064151](images\1562408064151.png)



#### 3.2.2 application.yml配置

![1562462174678](images\1562462174678.png)



#### 3.2.2 启动授权认证服务

启动之前，记得先启动eureka，再启动该授权认证工程。



### 3.3 Oauth2授权模式

#### 3.3.1 Oauth2授权模式

Oauth2有以下授权模式： 

```properties
1.授权码模式（Authorization Code）
2.隐式授权模式（Implicit） 
3.密码模式（Resource Owner Password Credentials） 
4.客户端模式（Client Credentials） 
```

其中授权码模式和密码模式应用较多，本小节介绍授权码模式。





#### 3.3.2 授权码授权实现

上边例举的黑马程序员网站使用QQ认证的过程就是授权码模式，流程如下： 

1、客户端请求第三方授权 

2、用户(资源拥有者)同意给客户端授权 

3、客户端获取到授权码，请求认证服务器申请 令牌 

4、认证服务器向客户端响应令牌 

5、客户端请求资源服务器的资源，资源服务校验令牌合法性，完成授权 

6、资源服务器返回受保护资源    



**(1)申请授权码**

请求认证服务获取授权码：

```
Get请求：
http://localhost:9001/oauth/authorize?client_id=changgou&response_type=code&scop=app&redirect_uri=http://localhost
```

参数列表如下： 

```properties
client_id：客户端id，和授权配置类中设置的客户端id一致。 
response_type：授权码模式固定为code 
scop：客户端范围，和授权配置类中设置的scop一致。 
redirect_uri：跳转uri，当授权码申请成功后会跳转到此地址，并在后边带上code参数（授权码）
```

 首先跳转到登录页面：

![1565035634997](images\1565035634997.png)

输入账号和密码，点击Login。 Spring Security接收到请求会调用UserDetailsService接口的loadUserByUsername方法查询用户正确的密码。 当前导入的基础工程中客户端ID为changgou，秘钥也为changgou即可认证通过。 

接下来进入授权页面：

![1565035586067](images\1565035586067.png)   

点击Authorize,接下来返回授权码： 认证服务携带授权码跳转redirect_uri,code=k45iLY就是返回的授权码

![1558181855325](images\1558181855325.png)



**(2)申请令牌**

拿到授权码后，申请令牌。 Post请求：http://localhost:9001/oauth/token 参数如下： 

```
grant_type：授权类型，填写authorization_code，表示授权码模式 
code：授权码，就是刚刚获取的授权码，注意：授权码只使用一次就无效了，需要重新申请。 
redirect_uri：申请授权码时的跳转url，一定和申请授权码时用的redirect_uri一致。 
```

此链接需要使用 http Basic认证。 什么是http Basic认证？ http协议定义的一种认证方式，将客户端id和客户端密码按照“客户端ID:客户端密码”的格式拼接，并用base64编 码，放在header中请求服务端，一个例子： Authorization：Basic WGNXZWJBcHA6WGNXZWJBcHA=WGNXZWJBcHA6WGNXZWJBcHA= 是用户名:密码的base64编码。 认证失败服务端返回 401 Unauthorized。

以上测试使用postman完成： 

http basic认证：    

![1558182132328](images\1558182132328.png)

![1558182177167](images\1558182177167.png)

客户端Id和客户端密码会匹配数据库oauth_client_details表中的客户端id及客户端密码。    

点击发送： 申请令牌成功    

![](images\1562408718454.png)

返回信如下:

```properties
access_token：访问令牌，携带此令牌访问资源 
token_type：有MAC Token与Bearer Token两种类型，两种的校验算法不同，RFC 6750建议Oauth2采用 Bearer Token（http://www.rfcreader.com/#rfc6750）。 
refresh_token：刷新令牌，使用此令牌可以延长访问令牌的过期时间。 
expires_in：过期时间，单位为秒。 
scope：范围，与定义的客户端范围一致。    
jti：当前token的唯一标识
```



**(3)令牌校验**

Spring Security Oauth2提供校验令牌的端点，如下： 

Get: http://localhost:9001/oauth/check_token?token= [access_token]

参数： 

token：令牌 

使用postman测试如下:

![1562409096845](images\1562409096845.png)

如果令牌校验失败，会出现如下结果：

![1562409180948](images\1562409180948.png)

如果令牌过期了，会如下如下结果：

![1562409507493](images\1562409507493.png)



**(4)刷新令牌**

刷新令牌是当令牌快过期时重新生成一个令牌，它于授权码授权和密码授权生成令牌不同，刷新令牌不需要授权码 也不需要账号和密码，只需要一个刷新令牌、客户端id和客户端密码。 

测试如下： Post：http://localhost:9001/oauth/token 

参数：    

grant_type： 固定为 refresh_token 

refresh_token：刷新令牌（注意不是access_token，而是refresh_token）    

![1562409616911](images\1562409616911.png)







#### 3.3.3 密码授权实现

**(1)认证**

密码模式（Resource Owner Password Credentials）与授权码模式的区别是申请令牌不再使用授权码，而是直接 通过用户名和密码即可申请令牌。 

测试如下： 

Post请求：http://localhost:9001/oauth/token 

参数： 

grant_type：密码模式授权填写password 

username：账号 

password：密码 

并且此链接需要使用 http Basic认证。

![1558221388787](images\1558221388787.png)

![1558221423631](images\1558221423631.png)



测试数据如下：

![1562462930730](images\1562462930730.png)



**(2)校验令牌**

Spring Security Oauth2提供校验令牌的端点，如下： 

Get: http://localhost:9001/oauth/check_token?token= 

参数： 

token：令牌 

使用postman测试如下:

![1558221673028](images\1558221673028.png)

返回结果：

```properties
{
    "companyId": null,
    "userpic": null,
    "scope": [
        "app"
    ],
    "name": null,
    "utype": null,
    "active": true,
    "id": null,
    "exp": 1990221534,
    "jti": "5b96666e-436b-4301-91b5-d89f9bbe6edb",
    "client_id": "changgou",
    "username": "szitheima"
}
```

exp：过期时间，long类型，距离1970年的秒数（new Date().getTime()可得到当前时间距离1970年的毫秒数）。 

user_name： 用户名 

client_id：客户端Id，在oauth_client_details中配置 

scope：客户端范围，在oauth_client_details表中配置 

jti：与令牌对应的唯一标识 companyId、userpic、name、utype、

id：这些字段是本认证服务在Spring Security基础上扩展的用户身份信息    



**(3)刷新令牌**

刷新令牌是当令牌快过期时重新生成一个令牌，它于授权码授权和密码授权生成令牌不同，刷新令牌不需要授权码 也不需要账号和密码，只需要一个刷新令牌、客户端id和客户端密码。 

测试如下： Post：http://localhost:9001/oauth/token 

参数：    

grant_type： 固定为 refresh_token 

refresh_token：刷新令牌（注意不是access_token，而是refresh_token）    

![1558221864957](images\1558221864957.png)

刷新令牌成功，会重新生成新的访问令牌和刷新令牌，令牌的有效期也比旧令牌长。 

刷新令牌通常是在令牌快过期时进行刷新 。







## 4 资源服务授权

### 4.1 资源服务授权流程

(1)传统授权流程

![1562479275302](images\1562479275302.png)

资源服务器授权流程如上图，客户端先去授权服务器申请令牌，申请令牌后，携带令牌访问资源服务器，资源服务器访问授权服务校验令牌的合法性，授权服务会返回校验结果，如果校验成功会返回用户信息给资源服务器，资源服务器如果接收到的校验结果通过了，则返回资源给客户端。

传统授权方法的问题是用户每次请求资源服务，资源服务都需要携带令牌访问认证服务去校验令牌的合法性，并根 据令牌获取用户的相关信息，性能低下。 



(2)公钥私钥授权流程

![1562406193809](images\1562406193809.png)

传统的授权模式性能低下，每次都需要请求授权服务校验令牌合法性，我们可以利用公钥私钥完成对令牌的加密，如果加密解密成功，则表示令牌合法，如果加密解密失败，则表示令牌无效不合法，合法则允许访问资源服务器的资源，解密失败，则不允许访问资源服务器资源。

上图的业务流程如下:

```properties
1、客户端请求认证服务申请令牌
2、认证服务生成令牌认证服务采用非对称加密算法，使用私钥生成令牌。
3、客户端携带令牌访问资源服务客户端在Http header 中添加： Authorization：Bearer 令牌。
4、资源服务请求认证服务校验令牌的有效性资源服务接收到令牌，使用公钥校验令牌的合法性。
5、令牌有效，资源服务向客户端响应资源信息
```



### 4.2 公钥私钥

在对称加密的时代，加密和解密用的是同一个密钥，这个密钥既用于加密，又用于解密。这样做有一个明显的缺点，如果两个人之间传输文件，两个人都要知道密钥，如果是三个人呢，五个人呢？于是就产生了非对称加密，用一个密钥进行加密（公钥），用另一个密钥进行解密（私钥）。



#### 4.2.1 公钥私钥原理

张三有两把钥匙，一把是公钥，另一把是私钥。

![1562470657125](images\1562470657125.png)

张三把公钥送给他的朋友们----李四、王五、赵六----每人一把。

![1562470847506](images\1562470847506.png)

李四要给张三写一封保密的信。她写完后用张三的公钥加密，就可以达到保密的效果。

![1562471063950](images\1562471063950.png)

张三收信后，用私钥解密，就看到了信件内容。这里要强调的是，只要张三的私钥不泄露，这封信就是安全的，即使落在别人手里，也无法解密。

![1562471429501](images\1562471429501.png)

张三给李四回信，决定采用"数字签名"。他写完后先用Hash函数，生成信件的摘要（digest）。张三将这个签名，附在信件下面，一起发给李四。

![1562472383322](images\1562472383322.png)

李四收信后，取下数字签名，用张三的公钥解密，得到信件的摘要。由此证明，这封信确实是张三发出的。李四再对信件本身使用Hash函数，将得到的结果，与上一步得到的摘要进行对比。如果两者一致，就证明这封信未被修改过。

![1562472956514](images\1562472956514.png)



#### 4.2.2 生成私钥公钥

Spring Security 提供对JWT的支持，本节我们使用Spring Security 提供的JwtHelper来创建JWT令牌，校验JWT令牌 等操作。 这里JWT令牌我们采用非对称算法进行加密，所以我们要先生成公钥和私钥。

(1)生成密钥证书 下边命令生成密钥证书，采用RSA 算法每个证书包含公钥和私钥 

创建一个文件夹，在该文件夹下执行如下命令行：

```properties
keytool -genkeypair -alias changgou -keyalg RSA -keypass changgou -keystore changgou.jks -storepass changgou 
```

Keytool 是一个java提供的证书管理工具 

```properties
-alias：密钥的别名 
-keyalg：使用的hash算法 
-keypass：密钥的访问密码 
-keystore：密钥库文件名，xc.keystore保存了生成的证书 
-storepass：密钥库的访问密码 
```



(2)查询证书信息

```properties
keytool -list -keystore changgou.jks
```



(3)删除别名 

```properties
keytool -delete -alias changgou -keystore changgou.jsk
```



#### 4.2.3 导出公钥 

openssl是一个加解密工具包，这里使用openssl来导出公钥信息。 

安装 openssl：http://slproweb.com/products/Win32OpenSSL.html 

安装资料目录下的Win64OpenSSL-1_1_0g.exe 

配置openssl的path环境变量，如下图：

![1565037190408](images\1565037190408.png)

本教程配置在C:\OpenSSL-Win64\bin

cmd进入changgou.jks文件所在目录执行如下命令(如下命令在windows下执行，会把-变成中文方式，请将它改成英文的-)： 

```shell
keytool -list -rfc --keystore changgou.jks | openssl x509 -inform pem -pubkey
```

![1558222853425](images\1558222853425.png)

下面段内容是公钥

```properties
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAm
t47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnh
cP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEm
oLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/
iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZS
xtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv
9QIDAQAB
-----END PUBLIC KEY-----
```

将上边的公钥拷贝到文本public.key文件中，合并为一行,可以将它放到需要实现授权认证的工程中。





#### 4.2.4 JWT令牌

(1)创建令牌数据

在changgou-user-oauth工程中创建测试类com.changgou.token.CreateJwtTest，使用它来创建令牌信息，代码如下：

```java
public class CreateJwtTest {

    /***
     * 创建令牌测试
     */
    @Test
    public void testCreateToken(){
        //证书文件路径
        String key_location="changgou.jks";
        //秘钥库密码
        String key_password="changgou";
        //秘钥密码
        String keypwd = "changgou";
        //秘钥别名
        String alias = "changgou";

        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);

        //创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,key_password.toCharArray());

        //读取秘钥对(公钥、私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypwd.toCharArray());

        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();

        //定义Payload
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "itheima");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");

        //生成Jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivate));

        //取出令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
```

运行后的结果如下：

```properties
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.IR9Qu9ZqYZ2gU2qgAziyT38UhEeL4Oi69ko-dzC_P9-Vjz40hwZDqxl8wZ-W2WAw1eWGIHV1EYDjg0-eilogJZ5UikyWw1bewXCpvlM-ZRtYQQqHFTlfDiVcFetyTayaskwa-x_BVS4pTWAskiaIKbKR4KcME2E5o1rEek-3YPkqAiZ6WP1UOmpaCJDaaFSdninqG0gzSCuGvLuG40x0Ngpfk7mPOecsIi5cbJElpdYUsCr9oXc53ROyfvYpHjzV7c2D5eIZu3leUPXRvvVAPJFEcSBiisxUSEeiGpmuQhaFZd1g-yJ1WQrixFvehMeLX2XU6W1nlL5ARTpQf_Jjiw
```



(2)解析令牌

上面创建令牌后，我们可以对JWT令牌进行解析，这里解析需要用到公钥，我们可以将之前生成的公钥public.key拷贝出来用字符串变量token存储，然后通过公钥解密。

在changgou-user-oauth创建测试类com.changgou.token.ParseJwtTest实现解析校验令牌数据，代码如下：

```java
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
```

运行后的结果如下：

![1562478682563](images\1562478682563.png)





## 5 认证开发

### 5.1 需求分析

用户登录的流程图如下：

![1562481462105](images\1562481462105.png)

执行流程： 

```properties
1、用户登录，请求认证服务 
2、认证服务认证通过，生成jwt令牌，将jwt令牌及相关信息写入cookie 
3、用户访问资源页面，带着cookie到网关 
4、网关从cookie获取token，如果存在token，则校验token合法性，如果不合法则拒绝访问，否则放行 
5、用户退出，请求认证服务，删除cookie中的token 
```



### 5.2 认证服务

#### 5.2.1 认证需求分析

认证服务需要实现的功能如下： 

1、登录接口 

前端post提交账号、密码等，用户身份校验通过，生成令牌，并将令牌写入cookie。 

2、退出接口 校验当前用户的身份为合法并且为已登录状态。 将令牌从cookie中删除。    

![1558224020588](images\1558224020588.png)





#### 5.2.2 工具封装

在changgou-user-oauth工程中添加如下工具对象，方便操作令牌信息。

创建com.changgou.oauth.util.AuthToken类，存储用户令牌数据，代码如下：

```java
public class AuthToken implements Serializable{

    //令牌信息
    String accessToken;
    //刷新token(refresh_token)
    String refreshToken;
    //jwt短令牌
    String jti;
    
    //...get...set
}
```

创建com.changgou.oauth.util.CookieUtil类，操作Cookie,代码如下：

```java
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }

    /**
     * 根据cookie名称读取cookie
     * @param request
     * @return map<cookieName,cookieValue>
     */

    public static Map<String,String> readCookie(HttpServletRequest request, String ... cookieNames) {
        Map<String,String> cookieMap = new HashMap<String,String>();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    String cookieValue = cookie.getValue();
                    for(int i=0;i<cookieNames.length;i++){
                        if(cookieNames[i].equals(cookieName)){
                            cookieMap.put(cookieName,cookieValue);
                        }
                    }
                }
            }
        return cookieMap;

    }
}
```



创建com.changgou.oauth.util.UserJwt类，封装SpringSecurity中User信息以及用户自身基本信息,代码如下：

```java
public class UserJwt extends User {
    private String id;    //用户ID
    private String name;  //用户名字

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    //...get...set
}
```





#### 5.2.3  业务层

![1562486044874](images\1562486044874.png)

如上图，我们现在实现一个认证流程，用户从页面输入账号密码，到认证服务的Controller层，Controller层调用Service层，Service层调用OAuth2.0的认证地址，进行密码授权认证操作，如果账号密码正确了，就返回令牌信息给Service层，Service将令牌信息给Controller层，Controller层将数据存入到Cookie中，再响应用户。



创建com.changgou.oauth.service.AuthService接口，并添加授权认证方法：

```java
public interface AuthService {

    /***
     * 授权认证方法
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);
}
```



创建com.changgou.oauth.service.impl.AuthServiceImpl实现类，实现获取令牌数据，这里认证获取令牌采用的是密码授权模式，用的是RestTemplate向OAuth服务发起认证请求，代码如下：

```java
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    /***
     * 授权认证方法
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        //申请令牌
        AuthToken authToken = applyToken(username,password,clientId, clientSecret);
        if(authToken == null){
            throw new RuntimeException("申请令牌失败");
        }
        return authToken;
    }


    /****
     * 认证方法
     * @param username:用户登录名字
     * @param password：用户密码
     * @param clientId：配置文件中的客户端ID
     * @param clientSecret：配置文件中的秘钥
     * @return
     */
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        //选中认证服务的地址
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        if (serviceInstance == null) {
            throw new RuntimeException("找不到对应的服务");
        }
        //获取令牌的url
        String path = serviceInstance.getUri().toString() + "/oauth/token";
        //定义body
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        //授权方式
        formData.add("grant_type", "password");
        //账号
        formData.add("username", username);
        //密码
        formData.add("password", password);
        //定义头
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", httpbasic(clientId, clientSecret));
        //指定 restTemplate当遇到400或401响应时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        Map map = null;
        try {
            //http请求spring security的申请令牌接口
            ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(path, HttpMethod.POST,new HttpEntity<MultiValueMap<String, String>>(formData, header), Map.class);
            //获取响应数据
            map = mapResponseEntity.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
        if(map == null || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null) {
            //jti是jwt令牌的唯一标识作为用户身份令牌
            throw new RuntimeException("创建令牌失败！");
        }

        //将响应数据封装成AuthToken对象
        AuthToken authToken = new AuthToken();
        //访问令牌(jwt)
        String accessToken = (String) map.get("access_token");
        //刷新令牌(jwt)
        String refreshToken = (String) map.get("refresh_token");
        //jti，作为用户的身份标识
        String jwtToken= (String) map.get("jti");
        authToken.setJti(jwtToken);
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        return authToken;
    }


    /***
     * base64编码
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String httpbasic(String clientId,String clientSecret){
        //将客户端id和客户端密码拼接，按“客户端id:客户端密码”
        String string = clientId+":"+clientSecret;
        //进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic "+new String(encode);
    }
}
```



#### 5.2.4  控制层

创建控制层com.changgou.oauth.controller.AuthController，编写用户登录授权方法，代码如下：

```java
@RestController
@RequestMapping(value = "/user")
public class AuthController {

    //客户端ID
    @Value("${auth.clientId}")
    private String clientId;

    //秘钥
    @Value("${auth.clientSecret}")
    private String clientSecret;

    //Cookie存储的域名
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    //Cookie生命周期
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public Result login(String username, String password) {
        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("用户名不允许为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new RuntimeException("密码不允许为空");
        }
        //申请令牌
        AuthToken authToken =  authService.login(username,password,clientId,clientSecret);

        //用户身份令牌
        String access_token = authToken.getAccessToken();
        //将令牌存储到cookie
        saveCookie(access_token);

        return new Result(true, StatusCode.OK,"登录成功！");
    }

    /***
     * 将令牌存储到cookie
     * @param token
     */
    private void saveCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","Authorization",token,cookieMaxAge,false);
    }
}
```





#### 5.2.5 测试认证接口

使用postman测试：

 Post请求：http://localhost:9001/user/login    

![1562489532494](images\1562489532494.png)









