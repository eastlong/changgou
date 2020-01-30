package com.changgou.order.controller;

import com.changgou.order.pojo.ReturnOrderItem;
import com.changgou.order.service.ReturnOrderItemService;
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
@RequestMapping("/returnOrderItem")
@CrossOrigin
public class ReturnOrderItemController {

    @Autowired
    private ReturnOrderItemService returnOrderItemService;

    /***
     * ReturnOrderItem分页条件搜索实现
     * @param returnOrderItem
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  ReturnOrderItem returnOrderItem, @PathVariable  int page, @PathVariable  int size){
        //调用ReturnOrderItemService实现分页条件查询ReturnOrderItem
        PageInfo<ReturnOrderItem> pageInfo = returnOrderItemService.findPage(returnOrderItem, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ReturnOrderItem分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ReturnOrderItemService实现分页查询ReturnOrderItem
        PageInfo<ReturnOrderItem> pageInfo = returnOrderItemService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param returnOrderItem
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<ReturnOrderItem>> findList(@RequestBody(required = false)  ReturnOrderItem returnOrderItem){
        //调用ReturnOrderItemService实现条件查询ReturnOrderItem
        List<ReturnOrderItem> list = returnOrderItemService.findList(returnOrderItem);
        return new Result<List<ReturnOrderItem>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //调用ReturnOrderItemService实现根据主键删除
        returnOrderItemService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ReturnOrderItem数据
     * @param returnOrderItem
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  ReturnOrderItem returnOrderItem,@PathVariable Long id){
        //设置主键值
        returnOrderItem.setId(id);
        //调用ReturnOrderItemService实现修改ReturnOrderItem
        returnOrderItemService.update(returnOrderItem);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ReturnOrderItem数据
     * @param returnOrderItem
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   ReturnOrderItem returnOrderItem){
        //调用ReturnOrderItemService实现添加ReturnOrderItem
        returnOrderItemService.add(returnOrderItem);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ReturnOrderItem数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ReturnOrderItem> findById(@PathVariable Long id){
        //调用ReturnOrderItemService实现根据主键查询ReturnOrderItem
        ReturnOrderItem returnOrderItem = returnOrderItemService.findById(id);
        return new Result<ReturnOrderItem>(true,StatusCode.OK,"查询成功",returnOrderItem);
    }

    /***
     * 查询ReturnOrderItem全部数据
     * @return
     */
    @GetMapping
    public Result<List<ReturnOrderItem>> findAll(){
        //调用ReturnOrderItemService实现查询所有ReturnOrderItem
        List<ReturnOrderItem> list = returnOrderItemService.findAll();
        return new Result<List<ReturnOrderItem>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
