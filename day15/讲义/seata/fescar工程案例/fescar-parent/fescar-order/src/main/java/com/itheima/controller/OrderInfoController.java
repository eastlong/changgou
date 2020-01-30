package com.itheima.controller;

import com.itheima.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@RestController
@RequestMapping("/orderInfo")
@CrossOrigin
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 增加订单
     * @param username
     * @param id
     * @param count
     */
    @PostMapping(value = "/add")
    public String add(@RequestParam(value = "name") String username, @RequestParam(value = "id") int id, @RequestParam(value = "count") int count){
        //添加订单
        orderInfoService.add(username,id,count);
        return "success";
    }

}
