package com.itheima;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
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
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
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


   /* //创建代理数据源 不使用原来的数据源

    @Primary
    @Bean("dataSourceProxy")
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    *//**
     * jdbctemplate 使用代理数据源
     *
     * @param
     * @return
     *//*
    @Primary
    @Bean("jdbcTemplate")
    @ConditionalOnClass(DataSourceProxy.class)
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "dataSourceProxy") DataSourceProxy dataSourceProxy) {
        return new JdbcTemplate(dataSourceProxy);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }*/


}
