# 第12章 微信支付

## 学习目标

- 能够说出微信支付开发的整体思路

- 生成支付二维码

- 查询支付状态

- 实现支付日志的生成与订单状态的修改、删除订单

- 支付状态回查

- MQ处理支付回调状态

- 定时处理订单状态





## 1 开发准备

### 1.1 开发文档

微信支付接口调用的整体思路：

按API要求组装参数，以XML方式发送（POST）给微信支付接口（URL）,微信支付接口也是以XML方式给予响应。程序根据返回的结果（其中包括支付URL）生成二维码或判断订单状态。

在线微信支付开发文档：

<https://pay.weixin.qq.com/wiki/doc/api/index.html>

如果你不能联网，请查阅讲义配套资源 （资源\配套软件\微信扫码支付\开发文档）

我们在本章课程中会用到”统一下单”和”查询订单”两组API  

 ```properties
1. appid：微信公众账号或开放平台APP的唯一标识
2. mch_id：商户号  (配置文件中的partner)
3. partnerkey：商户密钥
4. sign:数字签名, 根据微信官方提供的密钥和一套算法生成的一个加密信息, 就是为了保证交易的安全性
 ```





### 1.2 微信支付模式回顾

**模式二**

![1558448510488](images\1558448510488.png)

业务流程说明：

```properties
1.商户后台系统根据用户选购的商品生成订单。
2.用户确认支付后调用微信支付【统一下单API】生成预支付交易；
3.微信支付系统收到请求后生成预支付交易单，并返回交易会话的二维码链接code_url。
4.商户后台系统根据返回的code_url生成二维码。
5.用户打开微信“扫一扫”扫描二维码，微信客户端将扫码内容发送到微信支付系统。
6.微信支付系统收到客户端请求，验证链接有效性后发起用户支付，要求用户授权。
7.用户在微信客户端输入密码，确认支付后，微信客户端提交授权。
8.微信支付系统根据用户授权完成支付交易。
9.微信支付系统完成支付交易后给微信客户端返回交易结果，并将交易结果通过短信、微信消息提示用户。微信客户端展示支付交易结果页面。
10.微信支付系统通过发送异步消息通知商户后台系统支付结果。商户后台系统需回复接收情况，通知微信后台系统不再发送该单的支付通知。
11.未收到支付通知的情况，商户后台系统调用【查询订单API】。
12.商户确认订单已支付后给用户发货。
```



### 1.3 微信支付SDK

微信支付提供了SDK, 大家下载后打开源码，install到本地仓库。

![1537902584152](images\1537902584152.png)



课程配套的本地仓库已经提供jar包，所以安装SDK步骤省略。

使用微信支付SDK,在maven工程中引入依赖

````xml
<!--微信支付-->
<dependency>
    <groupId>com.github.wxpay</groupId>
    <artifactId>wxpay-sdk</artifactId>
    <version>0.0.3</version>
</dependency>
````



我们主要会用到微信支付SDK的以下功能：

获取随机字符串

```java
WXPayUtil.generateNonceStr()
```

MAP转换为XML字符串（自动添加签名）

```java
 WXPayUtil.generateSignedXml(param, partnerkey)
```

XML字符串转换为MAP

```java
WXPayUtil.xmlToMap(result)
```



为了方便微信支付开发，我们可以在`changgou-common`工程下引入依赖

```xml
<!--微信支付-->
<dependency>
    <groupId>com.github.wxpay</groupId>
    <artifactId>wxpay-sdk</artifactId>
    <version>0.0.3</version>
</dependency>
```



### 1.4 HttpClient工具类

HttpClient是Apache Jakarta Common下的子项目，用来提供高效的、最新的、功能丰富的支持HTTP协议的客户端编程工具包，并且它支持HTTP协议最新的版本和建议。HttpClient已经应用在很多的项目中，比如Apache Jakarta上很著名的另外两个开源项目Cactus和HTMLUnit都使用了HttpClient。

HttpClient通俗的讲就是模拟了浏览器的行为，如果我们需要在后端向某一地址提交数据获取结果，就可以使用HttpClient.

关于HttpClient（原生）具体的使用不属于我们本章的学习内容，我们这里这里为了简化HttpClient的使用，提供了工具类HttpClient（对原生HttpClient进行了封装）

HttpClient工具类代码：

```java
public class HttpClient {
    private String url;
    private Map<String, String> param;
    private int statusCode;
    private String content;
    private String xmlParam;
    private boolean isHttps;

    public boolean isHttps() {
        return isHttps;
    }

    public void setHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    public String getXmlParam() {
        return xmlParam;
    }

    public void setXmlParam(String xmlParam) {
        this.xmlParam = xmlParam;
    }

    public HttpClient(String url, Map<String, String> param) {
        this.url = url;
        this.param = param;
    }

    public HttpClient(String url) {
        this.url = url;
    }

    public void setParameter(Map<String, String> map) {
        param = map;
    }

    public void addParameter(String key, String value) {
        if (param == null)
            param = new HashMap<String, String>();
        param.put(key, value);
    }

    public void post() throws ClientProtocolException, IOException {
        HttpPost http = new HttpPost(url);
        setEntity(http);
        execute(http);
    }

    public void put() throws ClientProtocolException, IOException {
        HttpPut http = new HttpPut(url);
        setEntity(http);
        execute(http);
    }

    public void get() throws ClientProtocolException, IOException {
        if (param != null) {
            StringBuilder url = new StringBuilder(this.url);
            boolean isFirst = true;
            for (String key : param.keySet()) {
                if (isFirst) {
                    url.append("?");
                }else {
                    url.append("&");
                }
                url.append(key).append("=").append(param.get(key));
            }
            this.url = url.toString();
        }
        HttpGet http = new HttpGet(url);
        execute(http);
    }

    /**
     * set http post,put param
     */
    private void setEntity(HttpEntityEnclosingRequestBase http) {
        if (param != null) {
            List<NameValuePair> nvps = new LinkedList<NameValuePair>();
            for (String key : param.keySet()) {
                nvps.add(new BasicNameValuePair(key, param.get(key))); // 参数
            }
            http.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8)); // 设置参数
        }
        if (xmlParam != null) {
            http.setEntity(new StringEntity(xmlParam, Consts.UTF_8));
        }
    }

    private void execute(HttpUriRequest http) throws ClientProtocolException,
            IOException {
        CloseableHttpClient httpClient = null;
        try {
            if (isHttps) {
                SSLContext sslContext = new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustStrategy() {
                            // 信任所有
                            @Override
                            public boolean isTrusted(X509Certificate[] chain,
                                                     String authType)
                                    throws CertificateException {
                                return true;
                            }
                        }).build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        sslContext);
                httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                        .build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            CloseableHttpResponse response = httpClient.execute(http);
            try {
                if (response != null) {
                    if (response.getStatusLine() != null) {
                        statusCode = response.getStatusLine().getStatusCode();
                    }
                    HttpEntity entity = response.getEntity();
                    // 响应内容
                    content = EntityUtils.toString(entity, Consts.UTF_8);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() throws ParseException, IOException {
        return content;
    }
}
```



HttpClient工具类使用的步骤

```java
HttpClient client=new HttpClient(请求的url地址);
client.setHttps(true);//是否是https协议
client.setXmlParam(xmlParam);//发送的xml数据
client.post();//执行post请求
String result = client.getContent(); //获取结果
```



将HttpClient工具包放到common工程下并引入依赖，引入依赖后就可以直接使用上述的工具包了。

```xml
<!--httpclient支持-->
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
</dependency>
```



### 1.5 支付微服务搭建

(1)创建changgou-service-pay

创建支付微服务changgou-service-pay，只要实现支付相关操作。



(2)application.yml

创建application.yml，配置文件如下：

```properties
server:
  port: 18092
spring:
  application:
    name: pay
  main:
    allow-bean-definition-overriding: true
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
feign:
  hystrix:
    enabled: true
#hystrix 配置
hystrix:
  command:
    default:
      execution:
        timeout:
        #如果enabled设置为false，则请求超时交给ribbon控制
          enabled: true
        isolation:
          strategy: SEMAPHORE

#微信支付信息配置
weixin:
  appid: wx8397f8696b538317
  partner: 1473426802
  partnerkey: T6m9iK73b0kn9g5v426MKfHQH7X8rKwb
  notifyurl: http://www.itcast.cn
```

appid： 微信公众账号或开放平台APP的唯一标识

partner：财付通平台的商户账号

partnerkey：财付通平台的商户密钥

notifyurl:  回调地址



(3)启动类创建

在`changgou-service-pay`中创建`com.changgou.WeixinPayApplication`，代码如下：

```java
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaClient
public class WeixinPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinPayApplication.class,args);
    }
}
```



## 2 微信支付二维码生成

### 2.1需求分析与实现思路

在支付页面上生成支付二维码，并显示订单号和金额

用户拿出手机,打开微信扫描页面上的二维码,然后在微信中完成支付

![1558475798785](images\1558475798785.png)



### 2.2 实现思路

我们通过HttpClient工具类实现对远程支付接口的调用。

接口链接：https://api.mch.weixin.qq.com/pay/unifiedorder

具体参数参见“统一下单”API, 构建参数发送给统一下单的url ，返回的信息中有支付url，根据url生成二维码，显示的订单号和金额也在返回的信息中。

 

### 2.3 代码实现

(1)业务层

新增`com.changgou.service.WeixinPayService`接口，代码如下：

```java
public interface WeixinPayService {
    /*****
     * 创建二维码
     * @param out_trade_no : 客户端自定义订单编号
     * @param total_fee    : 交易金额,单位：分
     * @return
     */
    public Map createNative(String out_trade_no, String total_fee);
}
```



创建`com.changgou.service.impl.WeixinPayServiceImpl`类,并发送Post请求获取预支付信息，包含二维码扫码支付地址。代码如下：

```java
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.partnerkey}")
    private String partnerkey;

    @Value("${weixin.notifyurl}")
    private String notifyurl;

    /****
     * 创建二维码
     * @param out_trade_no : 客户端自定义订单编号
     * @param total_fee    : 交易金额,单位：分
     * @return
     */
    @Override
    public Map createNative(String out_trade_no, String total_fee){
        try {
            //1、封装参数
            Map param = new HashMap();
            param.put("appid", appid);                              //应用ID
            param.put("mch_id", partner);                           //商户ID号
            param.put("nonce_str", WXPayUtil.generateNonceStr());   //随机数
            param.put("body", "畅购");                            	//订单描述
            param.put("out_trade_no",out_trade_no);                 //商户订单号
            param.put("total_fee", total_fee);                      //交易金额
            param.put("spbill_create_ip", "127.0.0.1");           //终端IP
            param.put("notify_url", notifyurl);                    //回调地址
            param.put("trade_type", "NATIVE");                     //交易类型

            //2、将参数转成xml字符，并携带签名
            String paramXml = WXPayUtil.generateSignedXml(param, partnerkey);

            ///3、执行请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            httpClient.post();

            //4、获取参数
            String content = httpClient.getContent();
            Map<String, String> stringMap = WXPayUtil.xmlToMap(content);
            System.out.println("stringMap:"+stringMap);

            //5、获取部分页面所需参数
            Map<String,String> dataMap = new HashMap<String,String>();
            dataMap.put("code_url",stringMap.get("code_url"));
            dataMap.put("out_trade_no",out_trade_no);
            dataMap.put("total_fee",total_fee);

            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
```



(2) 控制层

创建`com.changgou.controller.WeixinPayController`,主要调用WeixinPayService的方法获取创建二维码的信息，代码如下：

```java
@RestController
@RequestMapping(value = "/weixin/pay")
@CrossOrigin
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/create/native")
    public Result createNative(String outtradeno, String money){
        Map<String,String> resultMap = weixinPayService.createNative(outtradeno,money);
        return new Result(true, StatusCode.OK,"创建二维码预付订单成功！",resultMap);
    }
}
```

这里我们订单号通过随机数生成，金额暂时写死，后续开发我们再对接业务系统得到订单号和金额

Postman测试`http://localhost:18092/weixin/pay/create/native?outtradeno=No000000001&money=1`

![1563241115226](images\1563241115226.png)



打开支付页面/pay.html，修改value路径，然后打开，会出现二维码，可以扫码试试

![1558476420961](images\1558476420961.png)

测试如下：

![1549718968481](images\1549718968481.png)





## 3 检测支付状态

### 3.1 需求分析

当用户支付成功后跳转到成功页面

![1558476664135](images\1558476664135.png)



当返回异常时跳转到错误页面

![1558476701485](images\1558476701485.png)



### 3.2 实现思路

我们通过HttpClient工具类实现对远程支付接口的调用。

接口链接：https://api.mch.weixin.qq.com/pay/orderquery

具体参数参见“查询订单”API, 我们在controller方法中轮询调用查询订单（间隔3秒），当返回状态为success时，我们会在controller方法返回结果。前端代码收到结果后跳转到成功页面。



### 3.3 代码实现

(1)业务层

修改`com.changgou.service.WeixinPayService`，新增方法定义

```java
/***
 * 查询订单状态
 * @param out_trade_no : 客户端自定义订单编号
 * @return
 */
public Map queryPayStatus(String out_trade_no);
```



在com.changgou.pay.service.impl.WeixinPayServiceImpl中增加实现方法

```java
/***
 * 查询订单状态
 * @param out_trade_no : 客户端自定义订单编号
 * @return
 */
@Override
public Map queryPayStatus(String out_trade_no) {
    try {
        //1.封装参数
        Map param = new HashMap();
        param.put("appid",appid);                            //应用ID
        param.put("mch_id",partner);                         //商户号
        param.put("out_trade_no",out_trade_no);              //商户订单编号
        param.put("nonce_str",WXPayUtil.generateNonceStr()); //随机字符

        //2、将参数转成xml字符，并携带签名
        String paramXml = WXPayUtil.generateSignedXml(param,partnerkey);

        //3、发送请求
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        httpClient.setHttps(true);
        httpClient.setXmlParam(paramXml);
        httpClient.post();

        //4、获取返回值，并将返回值转成Map
        String content = httpClient.getContent();
        return WXPayUtil.xmlToMap(content);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
```



(2)控制层

在`com.changgou.controller.WeixinPayController`新增方法，用于查询支付状态，代码如下：

上图代码如下：

```java
/***
 * 查询支付状态
 * @param outtradeno
 * @return
 */
@GetMapping(value = "/status/query")
public Result queryStatus(String outtradeno){
    Map<String,String> resultMap = weixinPayService.queryPayStatus(outtradeno);
    return new Result(true,StatusCode.OK,"查询状态成功！",resultMap);
}
```





## 4 订单状态操作准备工作

### 4.1 需求分析

![1558490059984](images\1558490059984.png)

我们现在系统还有个问题需要解决：支付后订单状态没有改变



流程回顾：

```properties
1.用户下单之后，订单数据会存入到MySQL中，同时会将订单对应的支付日志存入到Redis，以List+Hash的方式存储。
2.用户下单后，进入支付页面，支付页面调用支付系统，从微信支付获取二维码数据，并在页面生成支付二维码。
3.用户扫码支付后，微信支付服务器会通调用前预留的回调地址，并携带支付状态信息。
4.支付系统接到支付状态信息后，将支付状态信息发送给RabbitMQ
5.订单系统监听RabbitMQ中的消息获取支付状态，并根据支付状态修改订单状态
6.为了防止网络问题导致notifyurl没有接到对应数据，定时任务定时获取Redis中队列数据去微信支付接口查询状态，并定时更新对应状态。
```

需要做的工作：

```properties
1.创建订单时，同时将订单信息放到Redis中，以List和Hash各存一份
2.实现回调地址接收支付状态信息
3.将订单支付状态信息发送给RabbitMQ
4.订单系统中监听支付状态信息，如果是支付成功，修改订单状态，如果是支付失败，删除订单(或者改成支付失败)
5.防止网络异常无法接收到回调地址的支付信息，定时任务从Redis List中读取数据判断是否支付，如果支付了，修改订单状态，如果未支付，将支付信息放入队列，下次再检测，如果支付失败删除订单(或者改成支付失败)。
```



### 4.2 Redis存储订单信息

每次添加订单后，会根据订单检查用户是否是否支付成功，我们不建议每次都操作数据库，每次操作数据库会增加数据库的负载，我们可以选择将用户的订单信息存入一份到Redis中，提升读取速度。

修改`changgou-service-order`微服务的`com.changgou.order.service.impl.OrderServiceImpl`类中的`add`方法，如果是线上支付，将用户订单数据存入到Redis中,由于每次创建二维码，需要用到订单编号 ，所以也需要将添加的订单信息返回。

![1566106841171](images\1566106841171.png)

上图代码如下：

```java
/**
 * 增加Order
 * 金额校验:后台校验
 * @param order
 */
@Override
public Order add(Order order){
    //...略

    //修改库存
    skuFeign.decrCount(order.getUsername());

    //添加用户积分
    userFeign.addPoints(2);

    //线上支付，记录订单
    if(order.getPayType().equalsIgnoreCase("1")){
        //将支付记录存入到Reids namespace  key  value
        redisTemplate.boundHashOps("Order").put(order.getId(),order);
    }

    //删除购物车信息
    //redisTemplate.delete("Cart_" + order.getUsername());

    return order;
}
```



修改`com.changgou.order.controller.OrderController`的add方法，将订单对象返回，因为页面需要获取订单的金额和订单号用于创建二维码，代码如下：

![1566107006010](images\1566107006010.png)



### 4.3 修改订单状态

订单支付成功后，需要修改订单状态并持久化到数据库，修改订单的同时，需要将Redis中的订单删除，所以修改订单状态需要将订单日志也传过来，实现代码如下：

修改com.changgou.order.service.OrderService，添加修改订单状态方法，代码如下：

```java
/***
 * 根据订单ID修改订单状态
 * @param transactionid 交易流水号
 * @param orderId
 */
void updateStatus(String orderId,String transactionid);
```



修改com.changgou.order.service.impl.OrderServiceImpl，添加修改订单状态实现方法，代码如下：

```java
/***
 * 订单修改
 * @param orderId
 * @param transactionid  微信支付的交易流水号
 */
@Override
public void updateStatus(String orderId,String transactionid) {
    //1.修改订单
    Order order = orderMapper.selectByPrimaryKey(orderId);
    order.setUpdateTime(new Date());    //时间也可以从微信接口返回过来，这里为了方便，我们就直接使用当前时间了
    order.setPayTime(order.getUpdateTime());    //不允许这么写
    order.setTransactionId(transactionid);  //交易流水号
    order.setPayStatus("1");    //已支付
    orderMapper.updateByPrimaryKeySelective(order);

    //2.删除Redis中的订单记录
    redisTemplate.boundHashOps("Order").delete(orderId);
}
```





### 4.4 删除订单

如果用户订单支付失败了，或者支付超时了，我们需要删除用户订单，删除订单的同时需要回滚库存，这里回滚库存我们就不实现了，作为同学们的作业。实现如下：

修改`changgou-service-order`的com.changgou.order.service.OrderService，添加删除订单方法，我们只需要将订单id传入进来即可实现，代码如下：

```java
/***
 * 删除订单操作
 * @param id
 */
void deleteOrder(String id);
```



修改`changgou-service-order`的com.changgou.order.service.impl.OrderServiceImpl，添加删除订单实现方法，代码如下：

```java
/***
 * 订单的删除操作
 */
@Override
public void deleteOrder(String id) {
    //改状态
    Order order = (Order) redisTemplate.boundHashOps("Order").get(id);
    order.setUpdateTime(new Date());
    order.setPayStatus("2");    //支付失败
    orderMapper.updateByPrimaryKeySelective(order);

    //删除缓存
    redisTemplate.boundHashOps("Order").delete(id);
}
```





## 5 支付信息回调

### 5.1 接口分析

每次实现支付之后，微信支付都会将用户支付结果返回到指定路径，而指定路径是指创建二维码的时候填写的`notifyurl`参数,响应的数据以及相关文档参考一下地址：`https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_7&index=8`





#### 5.1.1 返回参数分析

通知参数如下：

| 字段名     | 变量名      | 必填 | 类型        | 示例值  | 描述    |
| :--------- | :---------- | :--- | :---------- | :------ | :------ |
| 返回状态码 | return_code | 是   | String(16)  | SUCCESS | SUCCESS |
| 返回信息   | return_msg  | 是   | String(128) | OK      | OK      |

以下字段在return_code为SUCCESS的时候有返回

| 字段名         | 变量名         | 必填 | 类型       | 示例值                       | 描述                                            |
| :------------- | :------------- | :--- | :--------- | :--------------------------- | :---------------------------------------------- |
| 公众账号ID     | appid          | 是   | String(32) | wx8888888888888888           | 微信分配的公众账号ID（企业号corpid即为此appId） |
| 业务结果       | result_code    | 是   | String(16) | SUCCESS                      | SUCCESS/FAIL                                    |
| 商户订单号     | out_trade_no   | 是   | String(32) | 1212321211201407033568112322 | 商户系统内部订单号                              |
| 微信支付订单号 | transaction_id | 是   | String(32) | 1217752501201407033233368018 | 微信支付订单号                                  |



#### 5.1.2 响应分析

回调地址接收到数据后，需要响应信息给微信服务器，告知已经收到数据，不然微信服务器会再次发送4次请求推送支付信息。

| 字段名     | 变量名      | 必填 | 类型        | 示例值  | 描述           |
| :--------- | :---------- | :--- | :---------- | :------ | :------------- |
| 返回状态码 | return_code | 是   | String(16)  | SUCCESS | 请按示例值填写 |
| 返回信息   | return_msg  | 是   | String(128) | OK      | 请按示例值填写 |

举例如下：

```xml
<xml>
  <return_code><![CDATA[SUCCESS]]></return_code>
  <return_msg><![CDATA[OK]]></return_msg>
</xml>
```



### 5.2 回调接收数据实现

修改`changgou-service-pay`微服务的com.changgou.pay.controller.WeixinPayController,添加回调方法，代码如下：

```java
/***
 * 支付回调
 * @param request
 * @return
 */
@RequestMapping(value = "/notify/url")
public String notifyUrl(HttpServletRequest request){
    InputStream inStream;
    try {
        //读取支付回调数据
        inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        // 将支付回调数据转换成xml字符串
        String result = new String(outSteam.toByteArray(), "utf-8");
        //将xml字符串转换成Map结构
        Map<String, String> map = WXPayUtil.xmlToMap(result);

        //响应数据设置
        Map respMap = new HashMap();
        respMap.put("return_code","SUCCESS");
        respMap.put("return_msg","OK");
        return WXPayUtil.mapToXml(respMap);
    } catch (Exception e) {
        e.printStackTrace();
        //记录错误日志
    }
    return null;
}
```





## 6 MQ处理支付回调状态

### 6.1 业务分析

![1558490059984](images\1558490059984.png)

支付系统是独立于其他系统的服务，不做相关业务逻辑操作，只做支付处理，所以回调地址接收微信服务返回的支付状态后，立即将消息发送给RabbitMQ，订单系统再监听支付状态数据，根据状态数据做出修改订单状态或者删除订单操作。



### 6.2 发送支付状态

(1)集成RabbitMQ

修改支付微服务，集成RabbitMQ，添加如下依赖：

```xml
<!--加入ampq-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```



这里我们建议在后台手动创建队列，并绑定队列。如果使用程序创建队列，可以按照如下方式实现。

修改application.yml，配置支付队列和交换机信息，代码如下：

```properties
#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
    queue:
      order: queue.order
    routing:
      key: queue.order
```



创建队列以及交换机并让队列和交换机绑定，修改com.changgou.WeixinPayApplication,添加如下代码：

```java
/***
 * 创建DirectExchange交换机
 * @return
 */
@Bean
public DirectExchange basicExchange(){
    return new DirectExchange(env.getProperty("mq.pay.exchange.order"), true,false);
}

/***
 * 创建队列
 * @return
 */
@Bean(name = "queueOrder")
public Queue queueOrder(){
    return new Queue(env.getProperty("mq.pay.queue.order"), true);
}

/****
 * 队列绑定到交换机上
 * @return
 */
@Bean
public Binding basicBinding(){
    return BindingBuilder.bind(queueOrder()).to(basicExchange()).with(env.getProperty("mq.pay.routing.key"));
}
```



#### 6.2.2 发送MQ消息

修改回调方法，在接到支付信息后，立即将支付信息发送给RabbitMQ，代码如下：

![1558578639907](images\1558578639907.png)

上图代码如下：

```java
@Value("${mq.pay.exchange.order}")
private String exchange;
@Value("${mq.pay.queue.order}")
private String queue;
@Value("${mq.pay.routing.key}")
private String routing;

@Autowired
private WeixinPayService weixinPayService;

@Autowired
private RabbitTemplate rabbitTemplate;

/***
 * 支付回调
 * @param request
 * @return
 */
@RequestMapping(value = "/notify/url")
public String notifyUrl(HttpServletRequest request){
    InputStream inStream;
    try {
        //读取支付回调数据
        inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        // 将支付回调数据转换成xml字符串
        String result = new String(outSteam.toByteArray(), "utf-8");
        //将xml字符串转换成Map结构
        Map<String, String> map = WXPayUtil.xmlToMap(result);
        //将消息发送给RabbitMQ
        rabbitTemplate.convertAndSend(exchange,routing, JSON.toJSONString(map));

        //响应数据设置
        Map respMap = new HashMap();
        respMap.put("return_code","SUCCESS");
        respMap.put("return_msg","OK");
        return WXPayUtil.mapToXml(respMap);
    } catch (Exception e) {
        e.printStackTrace();
        //记录错误日志
    }
    return null;
}
```



### 6.3 监听MQ消息处理订单

在订单微服务中，我们需要监听MQ支付状态消息，并实现订单数据操作。

#### 6.3.1 集成RabbitMQ

在订单微服务中，先集成RabbitMQ，再监听队列消息。

在pom.xml中引入如下依赖：

```xml
<!--加入ampq-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```



在application.yml中配置rabbitmq配置，代码如下：

![1558578894925](images\1558578894925.png)

在application.yml中配置队列名字，代码如下：

```properties
#位置支付交换机和队列
mq:
  pay:
    queue:
      order: queue.order
```





#### 6.3.2 监听消息修改订单

在订单微服务于中创建com.changgou.order.consumer.OrderPayMessageListener，并在该类中consumeMessage方法，用于监听消息，并根据支付状态处理订单，代码如下：

```java
@Component
@RabbitListener(queues = {"${mq.pay.queue.order}"})
public class OrderPayMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    /***
     * 接收消息
     */
    @RabbitHandler
    public void consumeMessage(String msg){
        //将数据转成Map
        Map<String,String> result = JSON.parseObject(msg,Map.class);

        //return_code=SUCCESS
        String return_code = result.get("return_code");
        //业务结果
        String result_code = result.get("result_code");

        //业务结果 result_code=SUCCESS/FAIL，修改订单状态
        if(return_code.equalsIgnoreCase("success") ){
            //获取订单号
            String outtradeno = result.get("out_trade_no");
            //业务结果
            if(result_code.equalsIgnoreCase("success")){
                if(outtradeno!=null){
                    //修改订单状态  out_trade_no
                    orderService.updateStatus(outtradeno,result.get("transaction_id"));
                }
            }else{
                //订单删除
                orderService.deleteOrder(outtradeno);
            }
        }

    }
}
```



## 7 定时处理订单状态(学员完成)

### 7.1 业务分析

在现实场景中，可能会出现这么种情况，就是用户支付后，有可能畅购服务网络不通或者服务器挂了，此时会导致回调地址无法接收到用户支付状态，这时候我们需要取微信服务器查询。所以我们之前订单信息的ID存入到了Redis队列，主要用于解决这种网络不可达造成支付状态无法回调获取的问题。

实现思路如下：

```properties
1.每次下单，都将订单存入到Reids List队列中
2.定时每5秒检查一次Redis 队列中是否有数据，如果有，则再去查询微信服务器支付状态
3.如果已支付，则修改订单状态
4.如果没有支付，是等待支付，则再将订单存入到Redis队列中，等会再次检查
5.如果是支付失败，直接删除订单信息并修改订单状态
```

