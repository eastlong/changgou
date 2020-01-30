package com.itheima.order.service;

import com.itheima.order.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.order.service *
 * @since 1.0
 */
@Service
public class OrderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    public void create(String userId, String commodityCode, Integer count) {
        int orderMoney = count * 100;//订单金额
        //创建订单
        jdbcTemplate.update("insert order_tbl(user_id,commodity_code,count,money) values(?,?,?,?)",
                new Object[]{userId, commodityCode, count, orderMoney});
        //扣减余额
        userFeignClient.reduce(userId, orderMoney);
    }
}
