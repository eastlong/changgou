package com.itheima.service.impl;

import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.itheima.dao.LogInfoMapper;
import com.itheima.feign.OrderInfoFeign;
import com.itheima.feign.UserInfoFeign;
import com.itheima.pojo.LogInfo;
import com.itheima.service.BusinessService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.service.impl
 ****/
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private OrderInfoFeign orderInfoFeign;

    @Autowired
    private UserInfoFeign userInfoFeign;

    @Autowired
    private LogInfoMapper logInfoMapper;

    /***
     * 下单
     * @param username
     * @param id
     * @param count
     */
    @GlobalTransactional(name = "add")
    @Override
    public void add(String username, int id, int count) {
        //添加订单日志
        LogInfo logInfo = new LogInfo();
        logInfo.setContent("添加订单数据---"+new Date());
        logInfo.setCreatetime(new Date());
        int logcount = logInfoMapper.insertSelective(logInfo);
        System.out.println("添加日志受影响行数："+logcount);

        //添加订单
        orderInfoFeign.add(username,id,count);

        //用户账户余额递减
        userInfoFeign.decrMoney(username,10);
    }
}
