package com.itheima.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="item")
public interface ItemInfoFeign {

    /**
     * 库存递减
     * @param id
     * @param count
     * @return
     */
    @PostMapping(value = "/itemInfo/decrCount")
    String decrCount(@RequestParam(value = "id") int id,@RequestParam(value = "count") int count);
}