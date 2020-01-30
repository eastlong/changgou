package com.changgou.goods.controller;

import com.changgou.goods.pojo.Para;
import com.changgou.goods.service.ParaService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;

import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/para")
@CrossOrigin
public class ParaController {

    @Autowired
    private ParaService paraService;

    /***
     * Para分页条件搜索实现
     * @param para
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) Para para, @PathVariable int page, @PathVariable int size) {
        //调用ParaService实现分页条件查询Para
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * Para分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用ParaService实现分页查询Para
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param para
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<Para>> findList(@RequestBody(required = false) Para para) {
        //调用ParaService实现条件查询Para
        List<Para> list = paraService.findList(para);
        return new Result<List<Para>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用ParaService实现根据主键删除
        paraService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 修改Para数据
     * @param para
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Para para, @PathVariable Integer id) {
        //设置主键值
        para.setId(id);
        //调用ParaService实现修改Para
        paraService.update(para);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 新增Para数据
     * @param para
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Para para) {
        //调用ParaService实现添加Para
        paraService.add(para);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 根据ID查询Para数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Para> findById(@PathVariable Integer id) {
        //调用ParaService实现根据主键查询Para
        Para para = paraService.findById(id);
        return new Result<Para>(true, StatusCode.OK, "查询成功", para);
    }

    /***
     * 查询Para全部数据
     * @return
     */
    @GetMapping
    public Result<List<Para>> findAll() {
        //调用ParaService实现查询所有Para
        List<Para> list = paraService.findAll();
        return new Result<List<Para>>(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据3 商品的分类的ID 查询该三级分类对应的参数的列表
     *
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    public Result<List<Para>> findParaByCateogryId(@PathVariable(name = "id") Integer id) {
        List<Para> paraList = paraService.findParaByCateogryId(id);
        return new Result<List<Para>>(true, StatusCode.OK, "参数列表查询成功", paraList);
    }


}
