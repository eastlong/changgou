package com.changgou.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.pojo.SeckillStatus;
import entity.SystemConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 监听秒杀的队列
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.seckill.listener *
 * @since 1.0
 */
@Component
@RabbitListener(queues = "queue.seckillorder")
public class SeckillOrderPayListener {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @RabbitHandler
    public void consumer(String message) throws Exception{
        System.out.println("消费端接收数据======="+ new Date());


        //1.获取数据 json格式的字符串
        Map<String,String> allMap = JSON.parseObject(message, Map.class);
        System.out.println(allMap);
        //2.转成MAP

        //3.判断 是否成功(通信是否成)
        if(allMap!=null) {
            String return_code = allMap.get("return_code");
            if("SUCCESS".equalsIgnoreCase(return_code)) {
                String result_code = allMap.get("result_code");
                String attach = allMap.get("attach");//json格式的字符串 (里面有用户名信息)
                Map<String,String> attachMap = JSON.parseObject(attach, Map.class);
                String username = attachMap.get("username");


                //获取seckillstatus 状态信息
               SeckillStatus seckillStatus= (SeckillStatus) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).get(username);


                if("SUCCESS".equalsIgnoreCase(result_code)) {
                    //4.判断业务状态是否成功  如果 成功  1.删除预订单 2.同步到数据库 3.删除排队标识 4.删除状态信息
                    //4.1 根据用户名redis中获取订单的数据
                    SeckillOrder  seckillOrder = (SeckillOrder) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).get(username);
                    //4.2 删除预订单
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).delete(username);
                    //4.3 同步到数据库中
                    seckillOrder.setStatus("1");//已经支付
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date paytime = simpleDateFormat.parse(allMap.get("time_end"));
                    seckillOrder.setPayTime(paytime);
                    seckillOrder.setTransactionId(allMap.get("transaction_id"));

                    seckillOrderMapper.insertSelective(seckillOrder);

                    //4.4 删除 防止重复排队的标识
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_QUEUE_REPEAT_KEY).delete(username);
                    //4.5 删除 排队标识
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).delete(username);

                }else{
                    //关闭微信订单  判断微信关闭订单的状态(1,已支付:调用方法 更新数据到数据库中.2 调用成功:(关闭订单成功:执行删除订单的业务 ) 3.系统错误: 人工处理.   )

                    //5.判断业务状态是否成功  如果 不成功 1.删除预订单 2.恢复库存 3.删除排队标识 4.删除状态信息
                    SeckillOrder  seckillOrder = (SeckillOrder) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).get(username);

                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).delete(username);


                    // 2.恢复库存  压入商品的超卖的问题的队列中
                    redisTemplate.boundListOps(SystemConstants.SEC_KILL_CHAOMAI_LIST_KEY_PREFIX + seckillOrder.getSeckillId()).leftPush(seckillOrder.getSeckillId());


                    //2.恢复库存  获取商品的数据 商品的库存+1

                    SeckillGoods  seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + seckillStatus.getTime()).get(seckillOrder.getSeckillId());
                    if(seckillGoods==null){//说明你买的是最后一个商品 在redis中被删除掉了
                        seckillGoods=seckillGoodsMapper.selectByPrimaryKey(seckillOrder.getSeckillId());
                    }


                    Long increment = redisTemplate.boundHashOps(SystemConstants.SECK_KILL_GOODS_COUNT_KEY).increment(seckillOrder.getSeckillId(), 1);

                    seckillGoods.setStockCount(increment.intValue());

                    //3 删除 防止重复排队的标识
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_QUEUE_REPEAT_KEY).delete(username);
                    //4 删除 排队标识
                    redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).delete(username);




                }
            }
        }
    }
}
