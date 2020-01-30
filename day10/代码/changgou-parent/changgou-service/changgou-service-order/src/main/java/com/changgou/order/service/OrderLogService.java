package com.changgou.order.service;

import com.changgou.order.pojo.OrderLog;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:admin
 * @Description:OrderLog业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface OrderLogService {

    /***
     * OrderLog多条件分页查询
     * @param orderLog
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderLog> findPage(OrderLog orderLog, int page, int size);

    /***
     * OrderLog分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<OrderLog> findPage(int page, int size);

    /***
     * OrderLog多条件搜索方法
     * @param orderLog
     * @return
     */
    List<OrderLog> findList(OrderLog orderLog);

    /***
     * 删除OrderLog
     * @param id
     */
    void delete(String id);

    /***
     * 修改OrderLog数据
     * @param orderLog
     */
    void update(OrderLog orderLog);

    /***
     * 新增OrderLog
     * @param orderLog
     */
    void add(OrderLog orderLog);

    /**
     * 根据ID查询OrderLog
     * @param id
     * @return
     */
     OrderLog findById(String id);

    /***
     * 查询所有OrderLog
     * @return
     */
    List<OrderLog> findAll();
}
