package com.changgou.user.controller;

import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
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
@RequestMapping("/areas")
@CrossOrigin
public class AreasController {

    @Autowired
    private AreasService areasService;

    /***
     * Areas分页条件搜索实现
     * @param areas
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Areas areas, @PathVariable  int page, @PathVariable  int size){
        //调用AreasService实现分页条件查询Areas
        PageInfo<Areas> pageInfo = areasService.findPage(areas, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Areas分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用AreasService实现分页查询Areas
        PageInfo<Areas> pageInfo = areasService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param areas
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Areas>> findList(@RequestBody(required = false)  Areas areas){
        //调用AreasService实现条件查询Areas
        List<Areas> list = areasService.findList(areas);
        return new Result<List<Areas>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用AreasService实现根据主键删除
        areasService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Areas数据
     * @param areas
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Areas areas,@PathVariable String id){
        //设置主键值
        areas.setAreaid(id);
        //调用AreasService实现修改Areas
        areasService.update(areas);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Areas数据
     * @param areas
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Areas areas){
        //调用AreasService实现添加Areas
        areasService.add(areas);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Areas数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Areas> findById(@PathVariable String id){
        //调用AreasService实现根据主键查询Areas
        Areas areas = areasService.findById(id);
        return new Result<Areas>(true,StatusCode.OK,"查询成功",areas);
    }

    /***
     * 查询Areas全部数据
     * @return
     */
    @GetMapping
    public Result<List<Areas>> findAll(){
        //调用AreasService实现查询所有Areas
        List<Areas> list = areasService.findAll();
        return new Result<List<Areas>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
