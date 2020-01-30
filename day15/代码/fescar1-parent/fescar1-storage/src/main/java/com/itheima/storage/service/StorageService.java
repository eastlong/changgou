package com.itheima.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.storage.service *
 * @since 1.0
 */
@Service
public class StorageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 扣减 库存
     *
     * @param commodityCode 商品的ID
     * @param count          数量
     */
    public void deduct(String commodityCode, int count) {
        jdbcTemplate.update("update storage_tbl set count = count - ? where commodity_code = ?",
                new Object[]{count, commodityCode});
    }
}
