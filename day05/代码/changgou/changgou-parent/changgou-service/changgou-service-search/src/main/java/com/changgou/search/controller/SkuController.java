package com.changgou.search.controller;

import com.changgou.search.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * controller
 * 用于接收页面传递的请求 来测试 导入数据
 * 实现搜索的功能
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.search.controller *
 * @since 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/search")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping("/import")
    public Result importEs() {

        skuService.importEs();
        return new Result(true, StatusCode.OK, "导入成功");
    }

    /**
     *
     * @param searchMap  搜索的条件 map
     * @return  resultMap  返回的结果 map
     */
    @PostMapping
    public Map search(@RequestBody(required = false) Map searchMap){
       return  skuService.search(searchMap);
    }
}
