package com.changgou.seckill.task;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.pojo.SeckillStatus;
import entity.IdWorker;
import entity.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.seckill.task *
 * @since 1.0
 */
@Component
public class MultiThreadingCreateOrder {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    //创建订单 异步处理 加入一个注解
    @Async
    public void createrOrder(){


        //从队列中获取抢单信息()
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps(SystemConstants.SEC_KILL_USER_QUEUE_KEY).rightPop();

        if(seckillStatus!=null) {





            //测试使用写死
            String time = seckillStatus.getTime();
            Long id = seckillStatus.getGoodsId();//秒杀商品的ID
            String username = seckillStatus.getUsername();

            //判断 先从队列中获取商品 ,如果能获取到,说明 有库存,如果获取不到,说明 没库存 卖完了 return.

            Object o = redisTemplate.boundListOps(SystemConstants.SEC_KILL_CHAOMAI_LIST_KEY_PREFIX + id).rightPop();
            if(o==null){
                //卖完了
                //清除 掉  防止重复排队的key
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_QUEUE_REPEAT_KEY).delete(username);
                //清除 掉  排队标识(存储用户的抢单信息)
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).delete(username);
                return ;
            }

            //1.根据商品的ID 获取秒杀商品的数据

            SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).get(id);
           /* //2.判断是否有库存  如果 没有  抛出异常 :卖完了
            if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
                throw new RuntimeException("卖完了");
            }*/

            //3.如果有库存

            //4.创建一个预订单
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setId(idWorker.nextId());//订单的ID
            seckillOrder.setSeckillId(id);//秒杀商品ID
            seckillOrder.setMoney(seckillGoods.getCostPrice());//金额
            seckillOrder.setUserId(username);//登录的用户名
            seckillOrder.setCreateTime(new Date());//创建时间
            seckillOrder.setStatus("0");//未支付

            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_ORDER_KEY).put(username, seckillOrder);

            //5.减库存
            //seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
            Long increment = redisTemplate.boundHashOps(SystemConstants.SECK_KILL_GOODS_COUNT_KEY).increment(id, -1L);
            seckillGoods.setStockCount(increment.intValue());

            //6.判断库存是是否为0  如果 是,更新到数据库中,删除掉redis中的秒杀商品
            if (seckillGoods.getStockCount() <= 0) {
                seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);//数据库的库存更新为0
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).delete(id);
            } else {
                //设置回redis中
                redisTemplate.boundHashOps(SystemConstants.SEC_KILL_GOODS_PREFIX + time).put(id, seckillGoods);

            }
            //7.创建订单成功()


            try {
                System.out.println("开始模拟下单操作=====start====" + new Date() + Thread.currentThread().getName());
                Thread.sleep(10000);
                System.out.println("开始模拟下单操作=====end====" + new Date() + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //创建订单成功了 修改用户的抢单的信息
            seckillStatus.setOrderId(seckillOrder.getId());
            seckillStatus.setStatus(2);//待支付的状态
            seckillStatus.setMoney(Float.valueOf(seckillOrder.getMoney()));
            //重新设置回redis中
            redisTemplate.boundHashOps(SystemConstants.SEC_KILL_USER_STATUS_KEY).put(username,seckillStatus);

        }

    }
}
