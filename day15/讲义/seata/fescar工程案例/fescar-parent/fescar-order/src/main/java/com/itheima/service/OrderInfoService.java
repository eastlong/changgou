package com.itheima.service;
import com.itheima.pojo.OrderInfo;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:OrderInfo业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OrderInfoService {

    /***
     * 添加订单
     * @param username
     * @param id
     * @param count
     */
    void add(String username, int id, int count);
}
