package com.itheima;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * 就是就是
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima *
 * @since 1.0
 */

@SpringBootApplication
@EnableEurekaClient
public class StorageApplication {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 初始化化 先进行数据删除新增
     */
    @PostConstruct
    public void initData() {
        //jdbcTemplate.update("delete from account_tbl");
       // jdbcTemplate.update("delete from order_tbl");
        jdbcTemplate.update("delete from storage_tbl");

        //jdbcTemplate.update("insert into account_tbl(user_id,money) values('U100000','10000') ");
        jdbcTemplate.update("insert into storage_tbl(commodity_code,count) values('C100000','200') ");
    }


}
