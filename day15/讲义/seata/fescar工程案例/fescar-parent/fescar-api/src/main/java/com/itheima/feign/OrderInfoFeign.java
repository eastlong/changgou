package com.itheima.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="order")
public interface OrderInfoFeign {

    /**
     * 增加订单
     * @param username
     * @param id
     * @param count
     */
    @PostMapping(value = "/orderInfo/add")
    String add(@RequestParam(value = "name") String username,@RequestParam(value = "id") int id,@RequestParam(value = "count") int count);
}