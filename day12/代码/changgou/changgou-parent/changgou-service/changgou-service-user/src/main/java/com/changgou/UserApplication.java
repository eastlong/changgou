package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou *
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
//mapper扫描 用通用的ampper扫描器
@MapperScan("com.changgou.user.dao")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
