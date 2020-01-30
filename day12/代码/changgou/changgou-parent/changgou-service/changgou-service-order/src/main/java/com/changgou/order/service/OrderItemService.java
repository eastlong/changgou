package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:OrderItem业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OrderItemService {

    /***
     * OrderItem多条件分页查询
     * @param orderItem
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderItem> findPage(OrderItem orderItem, int page, int size);

    /***
     * OrderItem分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderItem> findPage(int page, int size);

    /***
     * OrderItem多条件搜索方法
     * @param orderItem
     * @return
     */
    List<OrderItem> findList(OrderItem orderItem);

    /***
     * 删除OrderItem
     * @param id
     */
    void delete(String id);

    /***
     * 修改OrderItem数据
     * @param orderItem
     */
    void update(OrderItem orderItem);

    /***
     * 新增OrderItem
     * @param orderItem
     */
    void add(OrderItem orderItem);

    /**
     * 根据ID查询OrderItem
     * @param id
     * @return
     */
     OrderItem findById(String id);

    /***
     * 查询所有OrderItem
     * @return
     */
    List<OrderItem> findAll();
}
