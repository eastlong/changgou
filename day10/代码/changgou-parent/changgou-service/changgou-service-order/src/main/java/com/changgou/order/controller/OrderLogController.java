package com.changgou.order.controller;

import com.changgou.order.pojo.OrderLog;
import com.changgou.order.service.OrderLogService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/orderLog")
@CrossOrigin
public class OrderLogController {

    @Autowired
    private OrderLogService orderLogService;

    /***
     * OrderLog分页条件搜索实现
     * @param orderLog
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  OrderLog orderLog, @PathVariable  int page, @PathVariable  int size){
        //调用OrderLogService实现分页条件查询OrderLog
        PageInfo<OrderLog> pageInfo = orderLogService.findPage(orderLog, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * OrderLog分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用OrderLogService实现分页查询OrderLog
        PageInfo<OrderLog> pageInfo = orderLogService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param orderLog
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<OrderLog>> findList(@RequestBody(required = false)  OrderLog orderLog){
        //调用OrderLogService实现条件查询OrderLog
        List<OrderLog> list = orderLogService.findList(orderLog);
        return new Result<List<OrderLog>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用OrderLogService实现根据主键删除
        orderLogService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改OrderLog数据
     * @param orderLog
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  OrderLog orderLog,@PathVariable String id){
        //设置主键值
        orderLog.setId(id);
        //调用OrderLogService实现修改OrderLog
        orderLogService.update(orderLog);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增OrderLog数据
     * @param orderLog
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   OrderLog orderLog){
        //调用OrderLogService实现添加OrderLog
        orderLogService.add(orderLog);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询OrderLog数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OrderLog> findById(@PathVariable String id){
        //调用OrderLogService实现根据主键查询OrderLog
        OrderLog orderLog = orderLogService.findById(id);
        return new Result<OrderLog>(true,StatusCode.OK,"查询成功",orderLog);
    }

    /***
     * 查询OrderLog全部数据
     * @return
     */
    @GetMapping
    public Result<List<OrderLog>> findAll(){
        //调用OrderLogService实现查询所有OrderLog
        List<OrderLog> list = orderLogService.findAll();
        return new Result<List<OrderLog>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
