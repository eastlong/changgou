package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima
 ****/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.itheima.feign"})
@MapperScan(basePackages = {"com.itheima.dao"})
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class,args);
    }
}
