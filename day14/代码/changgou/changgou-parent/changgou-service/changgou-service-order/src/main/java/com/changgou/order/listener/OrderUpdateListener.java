package com.changgou.order.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.order.listener *
 * @since 1.0
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.order}")
public class OrderUpdateListener {


    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void handlerData(String msg) {
        //1.接收消息(有订单的ID  有transaction_id )
        Map<String, String> map = JSON.parseObject(msg, Map.class);
        //2.更新对营的订单的状态
        if (map != null) {
            if (map.get("return_code").equalsIgnoreCase("success")) {
                orderService.updateStatus(map.get("out_trade_no"), map.get("transaction_id"));
            } else {
                //删除订单 支付失败.....
            }
        }
    }
}
