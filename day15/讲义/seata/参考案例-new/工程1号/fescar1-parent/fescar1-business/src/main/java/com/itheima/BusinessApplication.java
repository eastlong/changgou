package com.itheima;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima *
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class BusinessApplication {


    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initData() {
        jdbcTemplate.update("delete from log_info");
    }

}
