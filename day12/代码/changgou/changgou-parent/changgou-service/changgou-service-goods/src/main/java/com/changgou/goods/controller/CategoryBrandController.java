package com.changgou.goods.controller;
import com.changgou.goods.pojo.CategoryBrand;
import com.changgou.goods.service.CategoryBrandService;
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
@RequestMapping("/categoryBrand")
@CrossOrigin
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    /***
     * CategoryBrand分页条件搜索实现
     * @param categoryBrand
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  CategoryBrand categoryBrand, @PathVariable  int page, @PathVariable  int size){
        //调用CategoryBrandService实现分页条件查询CategoryBrand
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findPage(categoryBrand, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * CategoryBrand分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用CategoryBrandService实现分页查询CategoryBrand
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param categoryBrand
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<CategoryBrand>> findList(@RequestBody(required = false)  CategoryBrand categoryBrand){
        //调用CategoryBrandService实现条件查询CategoryBrand
        List<CategoryBrand> list = categoryBrandService.findList(categoryBrand);
        return new Result<List<CategoryBrand>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用CategoryBrandService实现根据主键删除
        categoryBrandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改CategoryBrand数据
     * @param categoryBrand
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  CategoryBrand categoryBrand,@PathVariable Integer id){
        //设置主键值
        categoryBrand.setCategoryId(id);
        //调用CategoryBrandService实现修改CategoryBrand
        categoryBrandService.update(categoryBrand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增CategoryBrand数据
     * @param categoryBrand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   CategoryBrand categoryBrand){
        //调用CategoryBrandService实现添加CategoryBrand
        categoryBrandService.add(categoryBrand);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询CategoryBrand数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<CategoryBrand> findById(@PathVariable Integer id){
        //调用CategoryBrandService实现根据主键查询CategoryBrand
        CategoryBrand categoryBrand = categoryBrandService.findById(id);
        return new Result<CategoryBrand>(true,StatusCode.OK,"查询成功",categoryBrand);
    }

    /***
     * 查询CategoryBrand全部数据
     * @return
     */
    @GetMapping
    public Result<List<CategoryBrand>> findAll(){
        //调用CategoryBrandService实现查询所有CategoryBrand
        List<CategoryBrand> list = categoryBrandService.findAll();
        return new Result<List<CategoryBrand>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
