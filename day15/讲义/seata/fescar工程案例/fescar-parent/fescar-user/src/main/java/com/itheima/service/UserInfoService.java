package com.itheima.service;

/****
 * @Author:shenkunlin
 * @Description:UserInfo业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface UserInfoService {

    /***
     * 账户金额递减
     * @param username
     * @param money
     */
    void decrMoney(String username, int money);
}
