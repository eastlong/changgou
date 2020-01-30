package com.changgou.content.feign;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="user")
@RequestMapping("/oauthClientDetails")
public interface OauthClientDetailsFeign {

    /***
     * OauthClientDetails分页条件搜索实现
     * @param oauthClientDetails
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) OauthClientDetails oauthClientDetails, @PathVariable  int page, @PathVariable  int size);

    /***
     * OauthClientDetails分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param oauthClientDetails
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<OauthClientDetails>> findList(@RequestBody(required = false) OauthClientDetails oauthClientDetails);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable String id);

    /***
     * 修改OauthClientDetails数据
     * @param oauthClientDetails
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody OauthClientDetails oauthClientDetails,@PathVariable String id);

    /***
     * 新增OauthClientDetails数据
     * @param oauthClientDetails
     * @return
     */
    @PostMapping
    Result add(@RequestBody OauthClientDetails oauthClientDetails);

    /***
     * 根据ID查询OauthClientDetails数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<OauthClientDetails> findById(@PathVariable String id);

    /***
     * 查询OauthClientDetails全部数据
     * @return
     */
    @GetMapping
    Result<List<OauthClientDetails>> findAll();
}