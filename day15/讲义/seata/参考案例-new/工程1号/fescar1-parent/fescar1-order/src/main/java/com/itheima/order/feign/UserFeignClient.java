package com.itheima.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jimin.jm@alibaba-inc.com
 * @date 2019/06/14
 */
@FeignClient(name = "fescar-account")
@RequestMapping("/account")
public interface UserFeignClient {

    @RequestMapping("/reduce")
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") int money);
}
