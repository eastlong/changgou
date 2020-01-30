package com.itheima.fescar;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fescar.rm.datasource.DataSourceProxy;
import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/*****
 * @Author: www.itheima.com
 * @Description: com.itheima.fescar
 ****/
public class FescarAutoConfiguration {

    public static final String FESCAR_XID = "fescarXID";

    /***
     * 创建代理数据库
     * 会将und_log绑定到本地事务中
     * @param environment
     * @return
     */
    @Bean
    @Primary
    public DataSource dataSource(Environment environment){
        //创建数据源对象
        DruidDataSource dataSource = new DruidDataSource();
        //获取数据源链接地址
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        try {
            //设置数据库驱动
            dataSource.setDriver(DriverManager.getDriver(environment.getProperty("spring.datasource.url")));
        } catch (SQLException e) {
            throw new RuntimeException("无法识别驱动类型");
        }
        //获取数据库名字
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        //获取数据库密码
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        //将数据库封装成一个代理数据库
        return new DataSourceProxy(dataSource);
    }

    /***
     * 全局事务扫描器
     * 用来解析带有@GlobalTransactional注解的方法，然后采用AOP的机制控制事务
     * @param environment
     * @return
     */
    @Bean
    public GlobalTransactionScanner globalTransactionScanner(Environment environment){
        //事务分组名称
        String applicationName = environment.getProperty("spring.application.name");
        String groupName = environment.getProperty("fescar.group.name");
        if(applicationName == null){
            return new GlobalTransactionScanner(groupName == null ? "my_test_tx_group" : groupName);
        }else{
            return new GlobalTransactionScanner(applicationName, groupName == null ? "my_test_tx_group" : groupName);
        }
    }

    /***
     * 每次微服务和微服务之间相互调用
     * 要想控制全局事务，每次TM都会请求TC生成一个XID，每次执行下一个事务，也就是调用其他微服务的时候都需要将该XID传递过去
     * 所以我们可以每次请求的时候，都获取头中的XID，并将XID传递到下一个微服务
     * @param restTemplates
     * @return
     */
    @ConditionalOnBean({RestTemplate.class})
    @Bean
    public Object addFescarInterceptor(Collection<RestTemplate> restTemplates){
        restTemplates.stream()
                .forEach(restTemplate -> {
                    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
                    if(interceptors != null){
                        interceptors.add(fescarRestInterceptor());
                    }
                });



        return new Object();
    }

    @Bean
    public FescarRMRequestFilter fescarRMRequestFilter(){
        return new FescarRMRequestFilter();
    }

    @Bean
    public FescarRestInterceptor fescarRestInterceptor(){
        return new FescarRestInterceptor();
    }
}
