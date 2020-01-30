package com.changgou.order.controller;

import com.changgou.order.pojo.OrderConfig;
import com.changgou.order.service.OrderConfigService;
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
@RequestMapping("/orderConfig")
@CrossOrigin
public class OrderConfigController {

    @Autowired
    private OrderConfigService orderConfigService;

    /***
     * OrderConfig分页条件搜索实现
     * @param orderConfig
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  OrderConfig orderConfig, @PathVariable  int page, @PathVariable  int size){
        //调用OrderConfigService实现分页条件查询OrderConfig
        PageInfo<OrderConfig> pageInfo = orderConfigService.findPage(orderConfig, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * OrderConfig分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用OrderConfigService实现分页查询OrderConfig
        PageInfo<OrderConfig> pageInfo = orderConfigService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param orderConfig
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<OrderConfig>> findList(@RequestBody(required = false)  OrderConfig orderConfig){
        //调用OrderConfigService实现条件查询OrderConfig
        List<OrderConfig> list = orderConfigService.findList(orderConfig);
        return new Result<List<OrderConfig>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用OrderConfigService实现根据主键删除
        orderConfigService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改OrderConfig数据
     * @param orderConfig
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  OrderConfig orderConfig,@PathVariable Integer id){
        //设置主键值
        orderConfig.setId(id);
        //调用OrderConfigService实现修改OrderConfig
        orderConfigService.update(orderConfig);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增OrderConfig数据
     * @param orderConfig
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   OrderConfig orderConfig){
        //调用OrderConfigService实现添加OrderConfig
        orderConfigService.add(orderConfig);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询OrderConfig数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OrderConfig> findById(@PathVariable Integer id){
        //调用OrderConfigService实现根据主键查询OrderConfig
        OrderConfig orderConfig = orderConfigService.findById(id);
        return new Result<OrderConfig>(true,StatusCode.OK,"查询成功",orderConfig);
    }

    /***
     * 查询OrderConfig全部数据
     * @return
     */
    @GetMapping
    public Result<List<OrderConfig>> findAll(){
        //调用OrderConfigService实现查询所有OrderConfig
        List<OrderConfig> list = orderConfigService.findAll();
        return new Result<List<OrderConfig>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
