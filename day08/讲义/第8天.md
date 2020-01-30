# 第8章 微服务网关和Jwt令牌

## 学习目标

+ 掌握微服务网关的系统搭建

+ 了解什么是微服务网关以及它的作用

+ 掌握系统中心微服务的搭建

+ 掌握用户密码加密存储bcrypt

+ 了解JWT鉴权的介绍

+ ==掌握JWT的鉴权的使用==

  使用Jwt令牌来存储用户登录信息，在微服务网关中识别登录信息(用户的身份)

+ ==掌握网关使用JWT进行校验==

+ 掌握==网关==限流



## 1 微服务网关

### 1.1 微服务网关的概述

不同的微服务一般会有不同的网络地址，而外部客户端可能需要调用多个服务的接口才能完成一个业务需求，如果让客户端直接与各个微服务通信，会有以下的问题：

+ 客户端会多次请求不同的微服务，增加了客户端的复杂性
+ 存在跨域请求，在一定场景下处理相对复杂
+ 认证复杂，每个服务都需要独立认证
+ 难以重构，随着项目的迭代，可能需要重新划分微服务。例如，可能将多个服务合并成一个或者将一个服务拆分成多个。如果客户端直接与微服务通信，那么重构将会很难实施
+ 某些微服务可能使用了防火墙 / 浏览器不友好的协议，直接访问会有一定的困难

以上这些问题可以借助网关解决。

网关是介于客户端和服务器端之间的中间层，所有的外部请求都会先经过 网关这一层。也就是说，API 的实现方面更多的考虑业务逻辑，而安全、性能、监控可以交由 网关来做，这样既提高业务灵活性又不缺安全性，典型的架构图如图所示：

![1557824391432](images/1557824391432.png)

优点如下：

+ 安全 ，只有网关系统对外进行暴露，微服务可以隐藏在内网，通过防火墙保护。
+ 易于监控。可以在网关收集监控数据并将其推送到外部系统进行分析。
+ 易于认证。可以在网关上进行认证，然后再将请求转发到后端的微服务，而无须在每个微服务中进行认证。
+ 减少了客户端与各个微服务之间的交互次数
+ 易于统一授权。

总结：微服务网关就是一个系统，通过暴露该微服务网关系统，方便我们进行相关的鉴权，安全控制，日志统一处理，易于监控的相关功能。



### 1.2 微服务网关技术

实现微服务网关的技术有很多，

+ nginx  Nginx (tengine x) 是一个高性能的[HTTP](https://baike.baidu.com/item/HTTP)和[反向代理](https://baike.baidu.com/item/%E5%8F%8D%E5%90%91%E4%BB%A3%E7%90%86/7793488)web服务器，同时也提供了IMAP/POP3/SMTP服务
+ zuul ,Zuul 是 Netflix 出品的一个基于 JVM 路由和服务端的负载均衡器。
+ spring-cloud-gateway, 是spring 出品的 基于spring 的网关项目，集成断路器，路径重写，性能比Zuul好。

我们使用gateway这个网关技术，无缝衔接到基于spring cloud的微服务开发中来。

gateway官网：

https://spring.io/projects/spring-cloud-gateway



## 2 网关系统使用

### 2.1 需求分析

​	由于我们开发的系统 有包括前台系统和后台系统，后台的系统 给管理员使用。那么也需要调用各种微服务，所以我们针对 系统管理搭建一个网关系统。分析如下：

![1561959723219](images\1561959723219.png)



### 2.2 搭建后台网关系统

#### 2.2.1 搭建分析

由上可知道，由于 需要有多个网关，所以为了管理方便。我们新建一个项目，打包方式为pom,在里面建立各种网关系统模块即可。如图所示：

![1561961106274](images\1561961106274.png)



#### 2.2.2 工程搭建

(1)引入依赖

修改changgou-gateway工程，打包方式为pom

pom.xml如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>changgou-parent</artifactId>
        <groupId>com.changgou</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>changgou-gateway</artifactId>
    <packaging>pom</packaging>
    <modules>
        <module>changgou-gateway-web</module>
    </modules>

    <!--网关依赖-->
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>

</project>
```



在changgou-gateway工程中，创建 changgou-gateway-web工程，该网关主要用于对后台微服务进行一个调用操作，将多个微服务串联到一起。

pom.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>changgou-gateway</artifactId>
        <groupId>com.changgou</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>changgou-gateway-web</artifactId>
    <description>
        普通web请求网关
    </description>
</project>
```



(2)引导类

在changgou-gateway-web中创建一个引导类com.changgou.GatewayWebApplication，代码如下：

```java
@SpringBootApplication
@EnableEurekaClient
public class GatewayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class,args);
    }
}
```



(3)application.yml配置

在changgou-gateway-web的resources下创建application.yml,代码如下：

```yaml
spring:
  application:
    name: gateway-web
server:
  port: 8001
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
management:
  endpoint:
    gateway:
      enabled: true
    web:
      exposure:
        include: true
```



### 2.3 跨域配置

有时候，我们需要对所有微服务跨域请求进行处理，则可以在gateway中进行跨域支持。修改application.yml,添加如下代码：

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        cors-configurations:
          '[/**]': # 匹配所有请求
              allowedOrigins: "*" #跨域处理 允许所有的域
              allowedMethods: # 支持的方法
                - GET
                - POST
                - PUT
                - DELETE
```



最终文件如下：

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        cors-configurations:
          '[/**]': # 匹配所有请求
              allowedOrigins: "*" #跨域处理 允许所有的域
              allowedMethods: # 支持的方法
                - GET
                - POST
                - PUT
                - DELETE
  application:
    name: gateway-web
server:
  port: 8001
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
management:
  endpoint:
    gateway:
      enabled: true
    web:
      exposure:
        include: true
```





### 2.4 网关过滤配置

![1562033084310](images\1562033084310.png)

路由过滤器允许以某种方式修改传入的HTTP请求或传出的HTTP响应。 路径过滤器的范围限定为特定路径。 Spring Cloud Gateway包含许多内置的GatewayFilter工厂。如上图，根据请求路径路由到不同微服务去，这块可以使用Gateway的路由过滤功能实现。

过滤器 有 20 多个 实现 类， 包括 头部 过滤器、 路径 类 过滤器、 Hystrix 过滤器 和 变更 请求 URL 的 过滤器， 还有 参数 和 状态 码 等 其他 类型 的 过滤器。

内置的过滤器工厂有22个实现类，包括 头部过滤器、路径过滤器、Hystrix 过滤器 、请求URL 变更过滤器，还有参数和状态码等其他类型的过滤器。根据过滤器工厂的用途来划分，可以分为以下几种：Header、Parameter、Path、Body、Status、Session、Redirect、Retry、RateLimiter和Hystrix。



#### 2.4.1 Host 路由 

比如用户请求cloud.itheima.com的时候，可以将请求路由给http://localhost:18081服务处理，如下配置：

![1562042602553](images\1562042602553.png)

上图配置如下：

```properties
      routes:
            - id: changgou_goods_route
              uri: http://localhost:18081
              predicates:
              - Host=cloud.itheima.com**
```



测试请求`http://cloud.itheima.com:8001/brand`,效果如下：

![1562043847287](images\1562043847287.png)

注意：此时要想让cloud.itheima.com访问本地计算机，要配置`C:\Windows\System32\drivers\etc\hosts`文件,映射配置如下：

```
127.0.0.1 cloud.itheima.com
```



#### 2.4.2 路径匹配过滤配置

我们还可以根据请求路径实现对应的路由过滤操作，例如请求中以`/brand/`路径开始的请求，都直接交给`http://localhost:180801`服务处理，如下配置：

![1562043573738](images\1562043573738.png)

上图配置如下：

```properties
      routes:
            - id: changgou_goods_route
              uri: http://localhost:18081
              predicates:
              - Path=/brand**
```



测试请求`http://localhost:8001/brand`,效果如下：

![1562043787417](images\1562043787417.png)



#### 2.4.3 PrefixPath 过滤配置

用户每次请求路径的时候，我们可以给真实请求加一个统一前缀，例如用户请求`http://localhost:8001`的时候我们让它请求真实地址`http://localhost:8001/brand`，如下配置：

![1562044494647](images\1562044494647.png)

上图配置如下：

```properties
      routes:
            - id: changgou_goods_route
              uri: http://localhost:18081
              predicates:
              #- Host=cloud.itheima.com**
              - Path=/**
              filters:
              - PrefixPath=/brand
```



测试请求`http://localhost:8001/`效果如下：

![1562044174592](images\1562044174592.png)





#### 2.4.4 StripPrefix 过滤配置

很多时候也会有这么一种请求，用户请求路径是`/api/brand`,而真实路径是`/brand`，这时候我们需要去掉`/api`才是真实路径，此时可以使用SttripPrefix功能来实现路径的过滤操作，如下配置：

![1562045331974](images\1562045331974.png)

上图配置如下：

```properties
      routes:
            - id: changgou_goods_route
              uri: http://localhost:18081
              predicates:
              #- Host=cloud.itheima.com**
              - Path=/**
              filters:
              #- PrefixPath=/brand
              - StripPrefix=1
```



测试请求`http://localhost:8001/api/brand`,效果如下：

![1562045175964](images\1562045175964.png)





#### 2.4.5 LoadBalancerClient 路由过滤器(客户端负载均衡) 

上面的路由配置每次都会将请求给指定的`URL`处理，但如果在以后生产环境，并发量较大的时候，我们需要根据服务的名称判断来做负载均衡操作，可以使用`LoadBalancerClientFilter`来实现负载均衡调用。`LoadBalancerClientFilter`会作用在url以lb开头的路由，然后利用`loadBalancer`来获取服务实例，构造目标`requestUrl`，设置到`GATEWAY_REQUEST_URL_ATTR`属性中，供`NettyRoutingFilter`使用。

修改application.yml配置文件，代码如下：

![1562045841241](images\1562045841241.png)

上图配置如下：

```properties
      routes:
            - id: changgou_goods_route
              #uri: http://localhost:18081
              uri: lb://goods
              predicates:
              #- Host=cloud.itheima.com**
              - Path=/**
              filters:
              #- PrefixPath=/brand
              - StripPrefix=1
```



测试请求路径`http://localhost:8001/api/brand`

![1562045175964](images\1562045175964.png)



### 2.5 网关限流

网关可以做很多的事情，比如，限流，当我们的系统 被频繁的请求的时候，就有可能 将系统压垮，所以 为了解决这个问题，需要在每一个微服务中做限流操作，但是如果有了网关，那么就可以在网关系统做限流，因为所有的请求都需要先通过网关系统才能路由到微服务中。



#### 2.5.1 思路分析

![](images\1557909861570.png)



#### 2.5.2 令牌桶算法

令牌桶算法是比较常见的限流算法之一，大概描述如下：
1）所有的请求在处理之前都需要拿到一个可用的令牌才会被处理；
2）根据限流大小，设置按照一定的速率往桶里添加令牌；
3）桶设置最大的放置令牌限制，当桶满时、新添加的令牌就被丢弃或者拒绝；
4）请求达到后首先要获取令牌桶中的令牌，拿着令牌才可以进行其他的业务逻辑，处理完业务逻辑之后，将令牌直接删除；
5）令牌桶有最低限额，当桶中的令牌达到最低限额的时候，请求处理完之后将不会删除令牌，以此保证足够的限流

如下图：

![1557910299016](images/1557910299016.png)

这个算法的实现，有很多技术，Guaua是其中之一，redis客户端也有其实现。



#### 2.5.3 使用令牌桶进行请求次数限流

spring cloud gateway 默认使用redis的RateLimter限流算法来实现。所以我们要使用首先需要引入redis的依赖

(1)引入redis依赖

在changgou-gateway的pom.xml中引入redis的依赖

```xml
<!--redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
    <version>2.1.3.RELEASE</version>
</dependency>
```



(2)定义KeyResolver

在Applicatioin引导类中添加如下代码，KeyResolver用于计算某一个类型的限流的KEY也就是说，可以通过KeyResolver来指定限流的Key。

我们可以根据IP来限流，比如每个IP每秒钟只能请求一次，在GatewayWebApplication定义key的获取，获取客户端IP，将IP作为key，如下代码：

```java
/***
 * IP限流
 * @return
 */
@Bean(name="ipKeyResolver")
public KeyResolver userKeyResolver() {
    return new KeyResolver() {
        @Override
        public Mono<String> resolve(ServerWebExchange exchange) {
            //获取远程客户端IP
            String hostName = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            System.out.println("hostName:"+hostName);
            return Mono.just(hostName);
        }
    };
}
```



(3)修改application.yml中配置项，指定限制流量的配置以及REDIS的配置，如图

修改如下图：

![1562049737562](images\1562049737562.png)

配置代码如下：

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]': # 匹配所有请求
              allowedOrigins: "*" #跨域处理 允许所有的域
              allowedMethods: # 支持的方法
                - GET
                - POST
                - PUT
                - DELETE
      routes:
            - id: changgou_goods_route
              uri: lb://goods
              predicates:
              - Path=/api/brand**
              filters:
              - StripPrefix=1
              - name: RequestRateLimiter #请求数限流 名字不能随便写 ，使用默认的facatory
                args:
                  key-resolver: "#{@ipKeyResolver}"
                  redis-rate-limiter.replenishRate: 1
                  redis-rate-limiter.burstCapacity: 1

  application:
    name: gateway-web
  #Redis配置
  redis:
    host: 192.168.211.132
    port: 6379

server:
  port: 8001
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
management:
  endpoint:
    gateway:
      enabled: true
    web:
      exposure:
        include: true
```

解释：

`redis-rate-limiter.replenishRate`是您希望允许用户每秒执行多少请求，而不会丢弃任何请求。这是令牌桶填充的速率

`redis-rate-limiter.burstCapacity`是指令牌桶的容量，允许在一秒钟内完成的最大请求数,将此值设置为零将阻止所有请求。

 key-resolver: "#{@ipKeyResolver}" 用于通过SPEL表达式来指定使用哪一个KeyResolver.

如上配置：

表示 一秒内，允许 一个请求通过，令牌桶的填充速率也是一秒钟添加一个令牌。

最大突发状况 也只允许 一秒内有一次请求，可以根据业务来调整 。



多次请求会发生如下情况

![1562049920902](images\1562049920902.png)





## 3 用户登录

项目中有2个重要角色，分别为管理员和用户，下面几章我们将实现购物下单和支付，用户如果没登录是没法下单和支付的，所以我们这里需要实现一个登录功能。





### 3.1 表结构介绍

changgou_user表如下：

![1562050583653](images\1562050583653.png)



用户信息表tb_user

```sql
CREATE TABLE `tb_user` (
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime NOT NULL COMMENT '修改时间',
  `source_type` varchar(1) DEFAULT NULL COMMENT '会员来源：1:PC，2：H5，3：Android，4：IOS',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `status` varchar(1) DEFAULT NULL COMMENT '使用状态（1正常 0非正常）',
  `head_pic` varchar(150) DEFAULT NULL COMMENT '头像地址',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ号码',
  `is_mobile_check` varchar(1) DEFAULT '0' COMMENT '手机是否验证 （0否  1是）',
  `is_email_check` varchar(1) DEFAULT '0' COMMENT '邮箱是否检测（0否  1是）',
  `sex` varchar(1) DEFAULT '1' COMMENT '性别，1男，0女',
  `user_level` int(11) DEFAULT NULL COMMENT '会员等级',
  `points` int(11) DEFAULT NULL COMMENT '积分',
  `experience_value` int(11) DEFAULT NULL COMMENT '经验值',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月日',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
```



### 3.2 用户微服务创建

创建工程之前，先使用代码生成器生成对应的业务代码。



(1)公共API创建

在changgou-service-api中创建changgou-service-user-api，并将pojo拷贝到工程中，如下图：

![1562051835630](images\1562051835630.png)



在changgou-service中创建changgou-service-user微服务,并引入生成的业务逻辑代码，如下图：

![1562051932945](images\1562051932945.png)



(2)依赖

在changgou-service-user的pom.xml引入如下依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>changgou-service</artifactId>
        <groupId>com.changgou</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>changgou-service-user</artifactId>

    <!--依赖-->
    <dependencies>
        <dependency>
            <groupId>com.changgou</groupId>
            <artifactId>changgou-service-user-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```



(3)启动类创建

在changgou-service-user微服务中创建启动类com.changgou.UserApplication，代码如下：

```java
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.changgou.user.dao")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
```



(4)application.yml配置

在changgou-service-user的resources中创建application.yml配置，代码如下：

```properties
server:
  port: 18089
spring:
  application:
    name: user
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.211.132:3306/changgou_user?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    password: 123456
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
feign:
  hystrix:
    enabled: true
```





### 3.3 登录

登录的时候，需要进行密码校验，这里采用了BCryptPasswordEncoder进行加密，需要将资料中的BCrypt导入到common工程中，其中BCrypt.checkpw("明文","密文")用于对比密码是否一致。

修改changgou-service-user的com.changgou.user.controller.UserController添加登录方法，代码如下：

```java
/***
 * 用户登录
 */
@RequestMapping(value = "/login")
public Result login(String username,String password){
    //查询用户信息
    User user = userService.findById(username);

    if(user!=null && BCrypt.checkpw(password,user.getPassword())){
        return new Result(true,StatusCode.OK,"登录成功！",user);
    }
    return  new Result(false,StatusCode.LOGINERROR,"账号或者密码错误！");
}
```

注意：这里密码进行了加密。



使用Postman测试如下：

![1562054405004](images\1562054405004.png)



### 3.4 网关关联

![1562055045607](images\1562055045607.png)

在我们平时工作中，并不会直接将微服务暴露出去，一般都会使用网关对接，实现对微服务的一个保护作用，如上图，当用户访问`/api/user/`的时候我们再根据用户请求调用用户微服务的指定方法。当然，除了`/api/user/`还有`/api/address/`、`/api/areas/`、`/api/cities/`、`/api/provinces/`都需要由user微服务处理，修改网关工程`changgou-gateway-web`的application.yml配置文件，如下代码：

![1562055618797](images\1562055618797.png)

上图代码如下：

```properties
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]': # 匹配所有请求
              allowedOrigins: "*" #跨域处理 允许所有的域
              allowedMethods: # 支持的方法
                - GET
                - POST
                - PUT
                - DELETE
      routes:
            - id: changgou_goods_route
              uri: lb://goods
              predicates:
              - Path=/api/goods/**
              filters:
              - StripPrefix=1
              - name: RequestRateLimiter #请求数限流 名字不能随便写 ，使用默认的facatory
                args:
                  key-resolver: "#{@ipKeyResolver}"
                  redis-rate-limiter.replenishRate: 1
                  redis-rate-limiter.burstCapacity: 1
            #用户微服务
            - id: changgou_user_route
              uri: lb://user
              predicates:
              - Path=/api/user/**,/api/address/**,/api/areas/**,/api/cities/**,/api/provinces/**
              filters:
              - StripPrefix=1

  application:
    name: gateway-web
  #Redis配置
  redis:
    host: 192.168.211.132
    port: 6379

server:
  port: 8001
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
management:
  endpoint:
    gateway:
      enabled: true
    web:
      exposure:
        include: true
```



使用Postman访问`http://localhost:8001/api/user/login?username=changgou&password=changgou`，效果如下：

![1562055882671](images\1562055882671.png)





## 4 JWT讲解

### 4.1 需求分析

我们之前已经搭建过了网关，使用网关在网关系统中比较适合进行权限校验。

![1562059385713](images\1562059385713.png)

那么我们可以采用JWT的方式来实现鉴权校验。



### 4.2 什么是JWT

JSON Web Token（JWT）是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。



### 4.3 JWT的构成

一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签名。

**头部（Header）**

头部用于描述关于该JWT的最基本的信息，例如其类型以及签名所用的算法等。这也可以被表示成一个JSON对象。

```json
{"typ":"JWT","alg":"HS256"}
```

在头部指明了签名算法是HS256算法。 我们进行BASE64编码http://base64.xpcha.com/，编码后的字符串如下：

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
```

> 小知识：Base64是一种基于64个可打印字符来表示二进制数据的表示方法。由于2的6次方等于64，所以每6个比特为一个单元，对应某个可打印字符。三个字节有24个比特，对应于4个Base64单元，即3个字节需要用4个可打印字符来表示。JDK 中提供了非常方便的 **BASE64Encoder** 和 **BASE64Decoder**，用它们可以非常方便的完成基于 BASE64 的编码和解码

**载荷（playload）**

载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分

（1）标准中注册的声明（建议但不强制使用）

```
iss: jwt签发者
sub: jwt所面向的用户
aud: 接收jwt的一方
exp: jwt的过期时间，这个过期时间必须要大于签发时间
nbf: 定义在什么时间之前，该jwt都是不可用的.
iat: jwt的签发时间
jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
```

（2）公共的声明

公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.但不建议添加敏感信息，因为该部分在客户端可解密.   

（3）私有的声明

私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息。

这个指的就是自定义的claim。比如下面面结构举例中的admin和name都属于自定的claim。这些claim跟JWT标准规定的claim区别在于：JWT规定的claim，JWT的接收方在拿到JWT之后，都知道怎么对这些标准的claim进行验证(还不知道是否能够验证)；而private claims不会验证，除非明确告诉接收方要对这些claim进行验证以及规则才行。

定义一个payload:

```
{"sub":"1234567890","name":"John Doe","admin":true}
```

然后将其进行base64加密，得到Jwt的第二部分。

```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
```

**签证（signature）**

jwt的第三部分是一个签证信息，这个签证信息由三部分组成：

> header (base64后的)
>
> payload (base64后的)
>
> secret

这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。

```
TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```

将这三部分用.连接成一个完整的字符串,构成了最终的jwt:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```

**注意**：secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。



### 4.4 JJWT的介绍和使用

JJWT是一个提供端到端的JWT创建和验证的Java库。永远免费和开源(Apache License，版本2.0)，JJWT很容易使用和理解。它被设计成一个以建筑为中心的流畅界面，隐藏了它的大部分复杂性。

官方文档：

https://github.com/jwtk/jjwt



#### 4.4.1 创建TOKEN

(1)依赖引入

在changgou-parent项目中的pom.xml中添加依赖：

```xml
<!--鉴权-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>
```



(2)创建测试

在changgou-common的/test/java下创建测试类，并设置测试方法

```java
public class JwtTest {

    /****
     * 创建Jwt令牌
     */
    @Test
    public void testCreateJwt(){
        JwtBuilder builder= Jwts.builder()
                .setId("888")             //设置唯一编号
                .setSubject("小白")       //设置主题  可以是JSON数据
                .setIssuedAt(new Date())  //设置签发日期
                .signWith(SignatureAlgorithm.HS256,"itcast");//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //构建 并返回一个字符串
        System.out.println( builder.compact() );
    }
}
```

运行打印结果：

```properties
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjIyODd9.RBLpZ79USMplQyfJCZFD2muHV_KLks7M1ZsjTu6Aez4
```

再次运行，会发现每次运行的结果是不一样的，因为我们的载荷中包含了时间。



#### 4.4.2 TOKEN解析

我们刚才已经创建了token ，在web应用中这个操作是由服务端进行然后发给客户端，客户端在下次向服务端发送请求时需要携带这个token（这就好像是拿着一张门票一样），那服务端接到这个token 应该解析出token中的信息（例如用户id）,根据这些信息查询数据库返回相应的结果。

```java
/***
 * 解析Jwt令牌数据
 */
@Test
public void testParseJwt(){
    String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjIyODd9.RBLpZ79USMplQyfJCZFD2muHV_KLks7M1ZsjTu6Aez4";
    Claims claims = Jwts.parser().
            setSigningKey("itcast").
            parseClaimsJws(compactJwt).
            getBody();
    System.out.println(claims);
}
```

运行打印效果：

```
{jti=888, sub=小白, iat=1562062287}
```

试着将token或签名秘钥篡改一下，会发现运行时就会报错，所以解析token也就是验证token.



#### 4.4.3 设置过期时间

有很多时候，我们并不希望签发的token是永久生效的，所以我们可以为token添加一个过期时间。

##### 4.4.3.1 token过期设置

![1562062896413](images\1562062896413.png)

解释：

```properties
.setExpiration(date)//用于设置过期时间 ，参数为Date类型数据
```

运行，打印效果如下：

```properties
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjI5MjUsImV4cCI6MTU2MjA2MjkyNX0._vs4METaPkCza52LuN0-2NGGWIIO7v51xt40DHY1U1Q
```



##### 4.4.3.2 解析TOKEN

```java
/***
 * 解析Jwt令牌数据
 */
@Test
public void testParseJwt(){
    String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjI5MjUsImV4cCI6MTU2MjA2MjkyNX0._vs4METaPkCza52LuN0-2NGGWIIO7v51xt40DHY1U1Q";
    Claims claims = Jwts.parser().
            setSigningKey("itcast").
            parseClaimsJws(compactJwt).
            getBody();
    System.out.println(claims);
}
```

打印效果：

![1562063075099](images\1562063075099.png)

当前时间超过过期时间，则会报错。



#### 4.4.4 自定义claims

我们刚才的例子只是存储了id和subject两个信息，如果你想存储更多的信息（例如角色）可以定义自定义claims。

创建测试类，并设置测试方法：

创建token:

![1562063346685](images\1562063346685.png)



运行打印效果：

```properties
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjMyOTIsImFkZHJlc3MiOiLmt7HlnLPpu5Hpqazorq3nu4PokKXnqIvluo_lkZjkuK3lv4MiLCJuYW1lIjoi546L5LqUIiwiYWdlIjoyN30.ZSbHt5qrxz0F1Ma9rVHHAIy4jMCBGIHoNaaPQXxV_dk
```



解析TOKEN:

```JAVA
/***
 * 解析Jwt令牌数据
 */
@Test
public void testParseJwt(){
    String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjMyOTIsImFkZHJlc3MiOiLmt7HlnLPpu5Hpqazorq3nu4PokKXnqIvluo_lkZjkuK3lv4MiLCJuYW1lIjoi546L5LqUIiwiYWdlIjoyN30.ZSbHt5qrxz0F1Ma9rVHHAIy4jMCBGIHoNaaPQXxV_dk";
    Claims claims = Jwts.parser().
            setSigningKey("itcast").
            parseClaimsJws(compactJwt).
            getBody();
    System.out.println(claims);
}
```

运行效果：

![1562063412350](images\1562063412350.png)



### 4.5 鉴权处理

#### 4.5.1 思路分析

![1562069596308](images\1562069596308.png)

```properties
1.用户通过访问微服务网关调用微服务，同时携带头文件信息
2.在微服务网关这里进行拦截，拦截后获取用户要访问的路径
3.识别用户访问的路径是否需要登录，如果需要，识别用户的身份是否能访问该路径[这里可以基于数据库设计一套权限]
4.如果需要权限访问，用户已经登录，则放行
5.如果需要权限访问，且用户未登录，则提示用户需要登录
6.用户通过网关访问用户微服务，进行登录验证
7.验证通过后，用户微服务会颁发一个令牌给网关，网关会将用户信息封装到头文件中，并响应用户
8.用户下次访问，携带头文件中的令牌信息即可识别是否登录
```



#### 4.5.2用户登录签发TOKEN

(1)生成令牌工具类

在changgou-common中创建类entity.JwtUtil，主要辅助生成Jwt令牌信息，代码如下：

```java
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 3600000L;// 60 * 60 *1000  一个小时

    //Jwt令牌信息
    public static final String JWT_KEY = "itcast";

    public static String createJWT(String id, String subject, Long ttlMillis) {
        //指定算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //当前系统时间
        long nowMillis = System.currentTimeMillis();
        //令牌签发时间
        Date now = new Date(nowMillis);

        //如果令牌有效期为null，则默认设置有效期1小时
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }

        //令牌过期时间设置
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        //生成秘钥
        SecretKey secretKey = generalKey();

        //封装Jwt令牌信息
        JwtBuilder builder = Jwts.builder()
                .setId(id)                    //唯一的ID
                .setSubject(subject)          // 主题  可以是JSON数据
                .setIssuer("admin")          // 签发者
                .setIssuedAt(now)             // 签发时间
                .signWith(signatureAlgorithm, secretKey) // 签名算法以及密匙
                .setExpiration(expDate);      // 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(JwtUtil.JWT_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * 解析令牌数据
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
```



(2) 用户登录成功 则 签发TOKEN，修改登录的方法：

![1562070521132](images\1562070521132.png)

代码如下：

```java
/***
 * 用户登录
 */
@RequestMapping(value = "/login")
public Result login(String username,String password){
    //查询用户信息
    User user = userService.findById(username);

    if(user!=null && BCrypt.checkpw(password,user.getPassword())){
        //设置令牌信息
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("role","USER");
        info.put("success","SUCCESS");
        info.put("username",username);
        //生成令牌
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info),null);
        return new Result(true,StatusCode.OK,"登录成功！",jwt);
    }
    return  new Result(false,StatusCode.LOGINERROR,"账号或者密码错误！");
}
```



#### 4.5.3 网关过滤器拦截请求处理

拷贝JwtUtil到changgou-gateway-web中

![1562116408008](images\1562116408008.png)





#### 4.5.4 自定义全局过滤器

创建 过滤器类，如图所示：

![1562116467343](images\1562116467343.png)

AuthorizeFilter代码如下：

```java
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    /***
     * 全局过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //获取请求的URI
        String path = request.getURI().getPath();

        //如果是登录、goods等开放的微服务[这里的goods部分开放],则直接放行,这里不做完整演示，完整演示需要设计一套权限系统
        if (path.startsWith("/api/user/login") || path.startsWith("/api/brand/search/")) {
            //放行
            Mono<Void> filter = chain.filter(exchange);
            return filter;
        }

        //获取头文件中的令牌信息
        String tokent = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        //如果头文件中没有，则从请求参数中获取
        if (StringUtils.isEmpty(tokent)) {
            tokent = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }

        //如果为空，则输出错误代码
        if (StringUtils.isEmpty(tokent)) {
            //设置方法不允许被访问，405错误代码
           response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
           return response.setComplete();
        }

        //解析令牌数据
        try {
            Claims claims = JwtUtil.parseJWT(tokent);
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败，响应401错误
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //放行
        return chain.filter(exchange);
    }


    /***
     * 过滤器执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
```





#### 4.5.5 配置过滤规则

修改网关系统的yml文件：

![1562117570398](images\1562117570398.png)

上述代码如下：

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]': # 匹配所有请求
              allowedOrigins: "*" #跨域处理 允许所有的域
              allowedMethods: # 支持的方法
                - GET
                - POST
                - PUT
                - DELETE
      routes:
            - id: changgou_goods_route
              uri: lb://goods
              predicates:
              - Path=/api/album/**,/api/brand/**,/api/cache/**,/api/categoryBrand/**,/api/category/**,/api/para/**,/api/pref/**,/api/sku/**,/api/spec/**,/api/spu/**,/api/stockBack/**,/api/template/**
              filters:
              - StripPrefix=1
              - name: RequestRateLimiter #请求数限流 名字不能随便写 ，使用默认的facatory
                args:
                  key-resolver: "#{@ipKeyResolver}"
                  redis-rate-limiter.replenishRate: 1
                  redis-rate-limiter.burstCapacity: 1
            #用户微服务
            - id: changgou_user_route
              uri: lb://user
              predicates:
              - Path=/api/user/**,/api/address/**,/api/areas/**,/api/cities/**,/api/provinces/**
              filters:
              - StripPrefix=1

  application:
    name: gateway-web
  #Redis配置
  redis:
    host: 192.168.211.132
    port: 6379

server:
  port: 8001
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
management:
  endpoint:
    gateway:
      enabled: true
    web:
      exposure:
        include: true
```



测试访问`http://localhost:8001/api/user/login?username=changgou&password=changgou`，效果如下：

![1562117166529](images\1562117166529.png)



测试访问`http://localhost:8001/api/user`，效果如下：

![1562117230885](images\1562117230885.png)

参考官方手册：

https://cloud.spring.io/spring-cloud-gateway/spring-cloud-gateway.html#_stripprefix_gatewayfilter_factory





### 4.6 会话保持

用户每次请求的时候，我们都需要获取令牌数据，方法有多重，可以在每次提交的时候，将数据提交到头文件中，也可以将数据存储到Cookie中，每次从Cookie中校验数据，还可以每次将令牌数据以参数的方式提交到网关，这里面采用Cookie的方式比较容易实现。



#### 4.6.1 登录封装Cookie

修改user微服务，每次登录的时候，添加令牌信息到Cookie中，修改changgou-service-user的`com.changgou.user.controller.UserController`的`login`方法，代码如下：

![1562120506682](images\1562120506682.png)





#### 4.6.2 过滤器获取令牌数据

每次在网关中通过过滤器获取Cookie中的令牌，然后对令牌数据进行解析，修改微服务网关changgou-gateway-web中的AuthorizeFilter，代码如下：

![1562120763383](images\1562120763383.png)



登录后测试，可以识别用户身份，不登录无法识别。如下访问`http://localhost:8001/api/user`会携带令牌数据：

![1562121093670](images\1562121093670.png)





#### 4.6.3 添加Header信息

我们还可以在Gateway的全局过滤器中添加请求头信息，例如可以讲令牌信息添加到请求头中，在微服务中获取头信息，如下代码：

修改微服务网关中的AuthorizeFilter过滤器，在令牌信息校验那块将令牌加入到请求头中，如下代码：

![1562243515236](images\1562243515236.png)

在changgou-service-user微服务的UserController的findAll方法中获取请求头测试，代码如下：

![1562243608017](images\1562243608017.png)



后台输出令牌数据如下：

![1562243685686](images\1562243685686.png)

