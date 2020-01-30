package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;

import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.pay *
 * @since 1.0
 */
@RestController
@RequestMapping("/weixin/pay")
public class WeixinPayController {


    @Autowired
    private WeixinPayService weixinPayService;


    /**
     * 创建二维码连接地址返回给前端 生成二维码图片
     *
     * @param parameters  包含 订单号  包含 金额  包含 queue队列名称 交换机信息 路由信息 用户名
     * @return
     */
    @RequestMapping("/create/native")
    public Result<Map> createNative(@RequestParam Map<String,String> parameters) {
        //获取用户名

        Map<String, String> resultMap = weixinPayService.createNative(parameters);

        return new Result<Map>(true, StatusCode.OK, "二维码连接地址创建成功", resultMap);
    }

    /**
     * 根据交易订单号 来查询订单的状态
     *
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/status/query")
    public Result<Map> queryStatus(String out_trade_no) {
        Map<String, String> resultMap = weixinPayService.queryStatus(out_trade_no);
        return new Result<Map>(true, StatusCode.OK, "查询状态OK", resultMap);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /**
     * 接收 微信支付通知的结果  结果(以流的形式传递过来)
     */
    @RequestMapping("/notify/url")
    public String jieshouResult(HttpServletRequest request) {

        try {

            //1.获取流信息
            ServletInputStream ins = request.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();


            //todo
            byte[] buffer = new byte[1024];
            int len;
            while ((len = ins.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            bos.close();
            ins.close();

            //2.转换成XML字符串
            byte[] bytes = bos.toByteArray();

            //微信支付系统传递过来的XML的字符串
            String resultStrXML = new String(bytes, "utf-8");
            //3.转成MAP
            Map<String, String> map = WXPayUtil.xmlToMap(resultStrXML);

            System.out.println(resultStrXML);

            //4.发送消息给Rabbitmq  .........
            String data = JSON.toJSONString(map);


            //动态的从attach参数中获取数据
            String attach = map.get("attach");
            // {routingkey=queue.seckillorder, exchange=exchange.seckillorder, queue=queue.seckillorder, username=szitheima}
            Map<String,String> attachMap = JSON.parseObject(attach, Map.class);// 已经有


            System.out.println("通知中收到的attach的数据是:"+attachMap);


            //发送消息
            //rabbitTemplate.convertAndSend(env.getProperty("mq.pay.exchange.order"),env.getProperty("mq.pay.routing.key"),data);
            rabbitTemplate.convertAndSend(attachMap.get("exchange"),attachMap.get("routingkey"),data);

            //5.返回微信的接收请况(XML的字符串)

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("return_code", "SUCCESS");
            resultMap.put("return_msg", "OK");
            return WXPayUtil.mapToXml(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/test")
    public String test(){
        //动态的从attach参数中获取数据
        Map<String,String> attach = new HashMap<>();
        attach.put("username","zhangsan");
        attach.put("queue","queue.seckillorder");//队列名称
        attach.put("routingkey","queue.seckillorder");//路由key
        attach.put("exchange","exchange.seckillorder");
        // {routingkey=queue.seckillorder, exchange=exchange.seckillorder, queue=queue.seckillorder, username=szitheima}
        rabbitTemplate.convertAndSend(attach.get("exchange"),attach.get("routingkey"),"数据");

        return null;
    }

}
