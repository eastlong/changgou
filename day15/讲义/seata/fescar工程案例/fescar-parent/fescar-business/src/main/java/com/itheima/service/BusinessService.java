package com.itheima.service;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.service
 ****/
public interface BusinessService {

    /**
     * 下单
     * @param username
     * @param id
     * @param count
     */
    void add(String username, int id, int count);

}
