package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.order.service.impl *
 * @since 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private RedisTemplate redisTemplate;


    //创建feign (SKUfeign SPU的feign)

    @Override
    public void add(Long id, Integer num, String username) {

        if(num<=0){
            //删除掉原来的商品
            redisTemplate.boundHashOps("Cart_" + username).delete(id);
            return;
        }

        //1.根据商品的SKU的ID 获取sku的数据
        Result<Sku> skuResult = skuFeign.findById(id);

        Sku data = skuResult.getData();

        if (data != null) {

            //2.根据sku的数据对象 获取 该SKU对应的SPU的数据
            Long spuId = data.getSpuId();

            Result<Spu> spuResult = spuFeign.findById(spuId);
            Spu spu = spuResult.getData();

            //3.将数据存储到 购物车对象(order_item)中

            OrderItem orderItem = new OrderItem();

            orderItem.setCategoryId1(spu.getCategory1Id());
            orderItem.setCategoryId2(spu.getCategory2Id());
            orderItem.setCategoryId3(spu.getCategory3Id());
            orderItem.setSpuId(spu.getId());
            orderItem.setSkuId(id);
            orderItem.setName(data.getName());//商品的名称  sku的名称
            orderItem.setPrice(data.getPrice());//sku的单价
            orderItem.setNum(num);//购买的数量
            orderItem.setMoney(orderItem.getNum() * orderItem.getPrice());//单价* 数量
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());//单价* 数量
            orderItem.setImage(data.getImage());//商品的图片dizhi
            //4.数据添加到redis中  key:用户名 field:sku的ID  value:购物车数据(order_item)

            redisTemplate.boundHashOps("Cart_" + username).put(id, orderItem);// hset key field value   hget key field
        }

    }

    @Override
    public List<OrderItem> list(String username) {
        List<OrderItem> orderItemList = redisTemplate.boundHashOps("Cart_" + username).values();
        return orderItemList;
    }
}
