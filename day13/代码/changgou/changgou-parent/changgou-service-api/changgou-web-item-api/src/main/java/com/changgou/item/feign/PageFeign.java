package com.changgou.item.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.item.feign *
 * @since 1.0
 */
@FeignClient(name="item")
@RequestMapping("/page")
public interface PageFeign {

    /***
     * 根据SpuID生成静态页
     * @param id
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    Result createHtml(@PathVariable(name="id") Long id);
}
