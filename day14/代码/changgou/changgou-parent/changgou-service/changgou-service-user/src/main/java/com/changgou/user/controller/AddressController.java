package com.changgou.user.controller;

import com.changgou.user.config.TokenDecode;
import com.changgou.user.pojo.Address;
import com.changgou.user.service.AddressService;
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
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    /***
     * Address分页条件搜索实现
     * @param address
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Address address, @PathVariable  int page, @PathVariable  int size){
        //调用AddressService实现分页条件查询Address
        PageInfo<Address> pageInfo = addressService.findPage(address, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Address分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用AddressService实现分页查询Address
        PageInfo<Address> pageInfo = addressService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param address
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Address>> findList(@RequestBody(required = false)  Address address){
        //调用AddressService实现条件查询Address
        List<Address> list = addressService.findList(address);
        return new Result<List<Address>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用AddressService实现根据主键删除
        addressService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Address数据
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Address address,@PathVariable Integer id){
        //设置主键值
        address.setId(id);
        //调用AddressService实现修改Address
        addressService.update(address);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Address数据
     * @param address
     * @return
     */
    @PostMapping
    public Result add(@RequestBody   Address address){
        //调用AddressService实现添加Address
        addressService.add(address);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Address数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Address> findById(@PathVariable Integer id){
        //调用AddressService实现根据主键查询Address
        Address address = addressService.findById(id);
        return new Result<Address>(true,StatusCode.OK,"查询成功",address);
    }

    /***
     * 查询Address全部数据
     * @return
     */
    @GetMapping
    public Result<List<Address>> findAll(){
        //调用AddressService实现查询所有Address
        List<Address> list = addressService.findAll();
        return new Result<List<Address>>(true, StatusCode.OK,"查询成功",list) ;
    }

    @Autowired
    private TokenDecode tokenDecode;


    @RequestMapping("/user/list")
    public Result<List<Address>> list() {

        //获取当前登录的用户的信息
        String username = tokenDecode.getUserInfo().get("username");

        //调用服务层的方法 获取该用户的下的所有的地址列表
        List<Address> addressList = addressService.list(username);

        //返回
        return new Result<List<Address>>(true, StatusCode.OK, "地址列表查询成功", addressList);


    }
}
