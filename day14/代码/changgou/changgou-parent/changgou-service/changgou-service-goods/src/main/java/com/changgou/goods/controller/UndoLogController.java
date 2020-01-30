package com.changgou.goods.controller;
import com.changgou.goods.pojo.UndoLog;
import com.changgou.goods.service.UndoLogService;
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
@RequestMapping("/undoLog")
@CrossOrigin
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;

    /***
     * UndoLog分页条件搜索实现
     * @param undoLog
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  UndoLog undoLog, @PathVariable  int page, @PathVariable  int size){
        //调用UndoLogService实现分页条件查询UndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(undoLog, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * UndoLog分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用UndoLogService实现分页查询UndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param undoLog
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<UndoLog>> findList(@RequestBody(required = false)  UndoLog undoLog){
        //调用UndoLogService实现条件查询UndoLog
        List<UndoLog> list = undoLogService.findList(undoLog);
        return new Result<List<UndoLog>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //调用UndoLogService实现根据主键删除
        undoLogService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改UndoLog数据
     * @param undoLog
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  UndoLog undoLog,@PathVariable Long id){
        //设置主键值
        undoLog.setId(id);
        //调用UndoLogService实现修改UndoLog
        undoLogService.update(undoLog);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增UndoLog数据
     * @param undoLog
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   UndoLog undoLog){
        //调用UndoLogService实现添加UndoLog
        undoLogService.add(undoLog);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询UndoLog数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<UndoLog> findById(@PathVariable Long id){
        //调用UndoLogService实现根据主键查询UndoLog
        UndoLog undoLog = undoLogService.findById(id);
        return new Result<UndoLog>(true,StatusCode.OK,"查询成功",undoLog);
    }

    /***
     * 查询UndoLog全部数据
     * @return
     */
    @GetMapping
    public Result<List<UndoLog>> findAll(){
        //调用UndoLogService实现查询所有UndoLog
        List<UndoLog> list = undoLogService.findAll();
        return new Result<List<UndoLog>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
