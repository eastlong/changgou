package com.itheima.controller;

import com.itheima.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@RestController
@RequestMapping("/userInfo")
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /***
     * 账户余额递减
     * @param username
     * @param money
     */
    @PostMapping(value = "/add")
    public String decrMoney(@RequestParam(value = "username") String username, @RequestParam(value = "money") int money){
        userInfoService.decrMoney(username,money);
        return "success";
    }

}
