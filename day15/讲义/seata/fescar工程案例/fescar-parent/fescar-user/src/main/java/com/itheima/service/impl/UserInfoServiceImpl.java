package com.itheima.service.impl;

import com.itheima.dao.UserInfoMapper;
import com.itheima.pojo.UserInfo;
import com.itheima.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/****
 * @Author:shenkunlin
 * @Description:UserInfo业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /***
     * 账户金额递减
     * @param username
     * @param money
     */
    @Override
    public void decrMoney(String username, int money) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(username);
        userInfo.setMoney(userInfo.getMoney()-money);
        int count = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        System.out.println("添加用户受影响行数："+count);
        //int q=10/0;
    }
}
