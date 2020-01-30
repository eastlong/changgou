package com.itheima.business.service;

import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.itheima.business.feign.OrderFeignClient;
import com.itheima.business.feign.StorageFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.business.service *
 * @since 1.0
 */
@Service
public class BusinessService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private StorageFeignClient storageFeignClient;

    /**
     * 下采购订单
     *
     * @param userId        商家的ID
     * @param commodityCode 商品编码
     * @param count         购买的数量
     */
    //@GlobalTransactional//分布式事务的全局事务的入口 ()
    public void purchase(String userId, String commodityCode, int count) {
        //添加日志
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        jdbcTemplate.update("insert into log_info(content,createtime) values('测试','"+format+"') ");
        //调用feign      下单--->扣款
        orderFeignClient.create(userId, commodityCode, count);
        //调用feign      扣减库存
        int i=1/0;
        storageFeignClient.deduct(commodityCode, count);

    }
}
