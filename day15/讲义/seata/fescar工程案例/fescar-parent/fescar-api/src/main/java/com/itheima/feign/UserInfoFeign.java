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
@FeignClient(name="user")
public interface UserInfoFeign {

    /***
     * 账户余额递减
     * @param username
     * @param money
     */
    @PostMapping(value = "/userInfo/add")
    String decrMoney(@RequestParam(value = "username") String username,@RequestParam(value = "money") int money);
}