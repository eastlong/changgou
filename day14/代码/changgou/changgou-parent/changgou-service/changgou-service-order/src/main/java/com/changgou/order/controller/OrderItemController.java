package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.OrderItemService;
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
@RequestMapping("/orderItem")
@CrossOrigin
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    /***
     * OrderItem分页条件搜索实现
     * @param orderItem
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  OrderItem orderItem, @PathVariable  int page, @PathVariable  int size){
        //调用OrderItemService实现分页条件查询OrderItem
        PageInfo<OrderItem> pageInfo = orderItemService.findPage(orderItem, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * OrderItem分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用OrderItemService实现分页查询OrderItem
        PageInfo<OrderItem> pageInfo = orderItemService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param orderItem
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<OrderItem>> findList(@RequestBody(required = false)  OrderItem orderItem){
        //调用OrderItemService实现条件查询OrderItem
        List<OrderItem> list = orderItemService.findList(orderItem);
        return new Result<List<OrderItem>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用OrderItemService实现根据主键删除
        orderItemService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改OrderItem数据
     * @param orderItem
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  OrderItem orderItem,@PathVariable String id){
        //设置主键值
        orderItem.setId(id);
        //调用OrderItemService实现修改OrderItem
        orderItemService.update(orderItem);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增OrderItem数据
     * @param orderItem
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   OrderItem orderItem){
        //调用OrderItemService实现添加OrderItem
        orderItemService.add(orderItem);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询OrderItem数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OrderItem> findById(@PathVariable String id){
        //调用OrderItemService实现根据主键查询OrderItem
        OrderItem orderItem = orderItemService.findById(id);
        return new Result<OrderItem>(true,StatusCode.OK,"查询成功",orderItem);
    }

    /***
     * 查询OrderItem全部数据
     * @return
     */
    @GetMapping
    public Result<List<OrderItem>> findAll(){
        //调用OrderItemService实现查询所有OrderItem
        List<OrderItem> list = orderItemService.findAll();
        return new Result<List<OrderItem>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
