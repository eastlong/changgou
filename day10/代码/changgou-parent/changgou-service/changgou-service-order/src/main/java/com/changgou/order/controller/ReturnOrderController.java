package com.changgou.order.controller;

import com.changgou.order.pojo.ReturnOrder;
import com.changgou.order.service.ReturnOrderService;
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
@RequestMapping("/returnOrder")
@CrossOrigin
public class ReturnOrderController {

    @Autowired
    private ReturnOrderService returnOrderService;

    /***
     * ReturnOrder分页条件搜索实现
     * @param returnOrder
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  ReturnOrder returnOrder, @PathVariable  int page, @PathVariable  int size){
        //调用ReturnOrderService实现分页条件查询ReturnOrder
        PageInfo<ReturnOrder> pageInfo = returnOrderService.findPage(returnOrder, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ReturnOrder分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ReturnOrderService实现分页查询ReturnOrder
        PageInfo<ReturnOrder> pageInfo = returnOrderService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param returnOrder
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<ReturnOrder>> findList(@RequestBody(required = false)  ReturnOrder returnOrder){
        //调用ReturnOrderService实现条件查询ReturnOrder
        List<ReturnOrder> list = returnOrderService.findList(returnOrder);
        return new Result<List<ReturnOrder>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //调用ReturnOrderService实现根据主键删除
        returnOrderService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ReturnOrder数据
     * @param returnOrder
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  ReturnOrder returnOrder,@PathVariable Long id){
        //设置主键值
        returnOrder.setId(id);
        //调用ReturnOrderService实现修改ReturnOrder
        returnOrderService.update(returnOrder);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ReturnOrder数据
     * @param returnOrder
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   ReturnOrder returnOrder){
        //调用ReturnOrderService实现添加ReturnOrder
        returnOrderService.add(returnOrder);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ReturnOrder数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ReturnOrder> findById(@PathVariable Long id){
        //调用ReturnOrderService实现根据主键查询ReturnOrder
        ReturnOrder returnOrder = returnOrderService.findById(id);
        return new Result<ReturnOrder>(true,StatusCode.OK,"查询成功",returnOrder);
    }

    /***
     * 查询ReturnOrder全部数据
     * @return
     */
    @GetMapping
    public Result<List<ReturnOrder>> findAll(){
        //调用ReturnOrderService实现查询所有ReturnOrder
        List<ReturnOrder> list = returnOrderService.findAll();
        return new Result<List<ReturnOrder>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
