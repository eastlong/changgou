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
@RequestMapping("/provinces")
public interface ProvincesFeign {

    /***
     * Provinces分页条件搜索实现
     * @param provinces
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@RequestBody(required = false) Provinces provinces, @PathVariable  int page, @PathVariable  int size);

    /***
     * Provinces分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param provinces
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Provinces>> findList(@RequestBody(required = false) Provinces provinces);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable String id);

    /***
     * 修改Provinces数据
     * @param provinces
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Provinces provinces,@PathVariable String id);

    /***
     * 新增Provinces数据
     * @param provinces
     * @return
     */
    @PostMapping
    Result add(@RequestBody Provinces provinces);

    /***
     * 根据ID查询Provinces数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Provinces> findById(@PathVariable String id);

    /***
     * 查询Provinces全部数据
     * @return
     */
    @GetMapping
    Result<List<Provinces>> findAll();
}