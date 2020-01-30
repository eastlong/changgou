package com.itheima.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "fescar-order")
@RequestMapping("/order")
public interface OrderFeignClient {

    /**
     * 下单 并减余额
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    @RequestMapping("/create")
    void create(@RequestParam("userId") String userId,
                @RequestParam("commodityCode") String commodityCode,
                @RequestParam("count") Integer count);

}
