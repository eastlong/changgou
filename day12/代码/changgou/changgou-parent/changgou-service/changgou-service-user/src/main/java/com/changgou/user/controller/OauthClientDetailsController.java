package com.changgou.user.controller;

import com.changgou.user.pojo.OauthClientDetails;
import com.changgou.user.service.OauthClientDetailsService;
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
@RequestMapping("/oauthClientDetails")
@CrossOrigin
public class OauthClientDetailsController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    /***
     * OauthClientDetails分页条件搜索实现
     * @param oauthClientDetails
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  OauthClientDetails oauthClientDetails, @PathVariable  int page, @PathVariable  int size){
        //调用OauthClientDetailsService实现分页条件查询OauthClientDetails
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.findPage(oauthClientDetails, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * OauthClientDetails分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用OauthClientDetailsService实现分页查询OauthClientDetails
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param oauthClientDetails
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<OauthClientDetails>> findList(@RequestBody(required = false)  OauthClientDetails oauthClientDetails){
        //调用OauthClientDetailsService实现条件查询OauthClientDetails
        List<OauthClientDetails> list = oauthClientDetailsService.findList(oauthClientDetails);
        return new Result<List<OauthClientDetails>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用OauthClientDetailsService实现根据主键删除
        oauthClientDetailsService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改OauthClientDetails数据
     * @param oauthClientDetails
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  OauthClientDetails oauthClientDetails,@PathVariable String id){
        //设置主键值
        oauthClientDetails.setClientId(id);
        //调用OauthClientDetailsService实现修改OauthClientDetails
        oauthClientDetailsService.update(oauthClientDetails);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增OauthClientDetails数据
     * @param oauthClientDetails
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   OauthClientDetails oauthClientDetails){
        //调用OauthClientDetailsService实现添加OauthClientDetails
        oauthClientDetailsService.add(oauthClientDetails);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询OauthClientDetails数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<OauthClientDetails> findById(@PathVariable String id){
        //调用OauthClientDetailsService实现根据主键查询OauthClientDetails
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(id);
        return new Result<OauthClientDetails>(true,StatusCode.OK,"查询成功",oauthClientDetails);
    }

    /***
     * 查询OauthClientDetails全部数据
     * @return
     */
    @GetMapping
    public Result<List<OauthClientDetails>> findAll(){
        //调用OauthClientDetailsService实现查询所有OauthClientDetails
        List<OauthClientDetails> list = oauthClientDetailsService.findAll();
        return new Result<List<OauthClientDetails>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
