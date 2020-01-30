package com.changgou.goods.controller;
import com.changgou.goods.pojo.StockBack;
import com.changgou.goods.service.StockBackService;
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
@RequestMapping("/stockBack")
@CrossOrigin
public class StockBackController {

    @Autowired
    private StockBackService stockBackService;

    /***
     * StockBack分页条件搜索实现
     * @param stockBack
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  StockBack stockBack, @PathVariable  int page, @PathVariable  int size){
        //调用StockBackService实现分页条件查询StockBack
        PageInfo<StockBack> pageInfo = stockBackService.findPage(stockBack, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * StockBack分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用StockBackService实现分页查询StockBack
        PageInfo<StockBack> pageInfo = stockBackService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param stockBack
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<StockBack>> findList(@RequestBody(required = false)  StockBack stockBack){
        //调用StockBackService实现条件查询StockBack
        List<StockBack> list = stockBackService.findList(stockBack);
        return new Result<List<StockBack>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用StockBackService实现根据主键删除
        stockBackService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改StockBack数据
     * @param stockBack
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  StockBack stockBack,@PathVariable String id){
        //设置主键值
        stockBack.setSkuId(id);
        //调用StockBackService实现修改StockBack
        stockBackService.update(stockBack);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增StockBack数据
     * @param stockBack
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   StockBack stockBack){
        //调用StockBackService实现添加StockBack
        stockBackService.add(stockBack);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询StockBack数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<StockBack> findById(@PathVariable String id){
        //调用StockBackService实现根据主键查询StockBack
        StockBack stockBack = stockBackService.findById(id);
        return new Result<StockBack>(true,StatusCode.OK,"查询成功",stockBack);
    }

    /***
     * 查询StockBack全部数据
     * @return
     */
    @GetMapping
    public Result<List<StockBack>> findAll(){
        //调用StockBackService实现查询所有StockBack
        List<StockBack> list = stockBackService.findAll();
        return new Result<List<StockBack>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
