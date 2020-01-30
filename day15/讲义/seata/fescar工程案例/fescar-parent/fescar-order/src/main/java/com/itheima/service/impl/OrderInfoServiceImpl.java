package com.itheima.service.impl;

import com.itheima.dao.OrderInfoMapper;
import com.itheima.feign.ItemInfoFeign;
import com.itheima.pojo.OrderInfo;
import com.itheima.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/****
 * @Author:shenkunlin
 * @Description:OrderInfo业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ItemInfoFeign itemInfoFeign;

    /***
     * 添加订单
     * @param username
     * @param id
     * @param count
     */
    @Override
    public void add(String username, int id, int count) {
        //添加订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setMessage("生成订单");
        orderInfo.setMoney(10);
        int icount = orderInfoMapper.insertSelective(orderInfo);
        System.out.println("添加订单受影响函数："+icount);

        //递减库存
        itemInfoFeign.decrCount(id,count);
    }
}
