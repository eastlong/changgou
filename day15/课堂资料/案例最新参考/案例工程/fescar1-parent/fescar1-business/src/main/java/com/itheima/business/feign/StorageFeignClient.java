package com.itheima.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "fescar-storage")
@RequestMapping("/storage")
public interface StorageFeignClient {

    /**
     * 扣减库存
     *
     * @param commodityCode
     * @param count
     */
    @RequestMapping("/deduct")
    public Boolean deduct(@RequestParam(name="commodityCode") String commodityCode
            ,@RequestParam(name="count") Integer count);

}
