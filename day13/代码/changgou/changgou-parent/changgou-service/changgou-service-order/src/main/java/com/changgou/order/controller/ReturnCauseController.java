package com.changgou.order.controller;

import com.changgou.order.pojo.ReturnCause;
import com.changgou.order.service.ReturnCauseService;
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
@RequestMapping("/returnCause")
@CrossOrigin
public class ReturnCauseController {

    @Autowired
    private ReturnCauseService returnCauseService;

    /***
     * ReturnCause分页条件搜索实现
     * @param returnCause
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  ReturnCause returnCause, @PathVariable  int page, @PathVariable  int size){
        //调用ReturnCauseService实现分页条件查询ReturnCause
        PageInfo<ReturnCause> pageInfo = returnCauseService.findPage(returnCause, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ReturnCause分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ReturnCauseService实现分页查询ReturnCause
        PageInfo<ReturnCause> pageInfo = returnCauseService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param returnCause
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<ReturnCause>> findList(@RequestBody(required = false)  ReturnCause returnCause){
        //调用ReturnCauseService实现条件查询ReturnCause
        List<ReturnCause> list = returnCauseService.findList(returnCause);
        return new Result<List<ReturnCause>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用ReturnCauseService实现根据主键删除
        returnCauseService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ReturnCause数据
     * @param returnCause
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  ReturnCause returnCause,@PathVariable Integer id){
        //设置主键值
        returnCause.setId(id);
        //调用ReturnCauseService实现修改ReturnCause
        returnCauseService.update(returnCause);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ReturnCause数据
     * @param returnCause
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   ReturnCause returnCause){
        //调用ReturnCauseService实现添加ReturnCause
        returnCauseService.add(returnCause);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ReturnCause数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ReturnCause> findById(@PathVariable Integer id){
        //调用ReturnCauseService实现根据主键查询ReturnCause
        ReturnCause returnCause = returnCauseService.findById(id);
        return new Result<ReturnCause>(true,StatusCode.OK,"查询成功",returnCause);
    }

    /***
     * 查询ReturnCause全部数据
     * @return
     */
    @GetMapping
    public Result<List<ReturnCause>> findAll(){
        //调用ReturnCauseService实现查询所有ReturnCause
        List<ReturnCause> list = returnCauseService.findAll();
        return new Result<List<ReturnCause>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
