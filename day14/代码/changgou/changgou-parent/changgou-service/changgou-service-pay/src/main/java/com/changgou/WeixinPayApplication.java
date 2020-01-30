package com.changgou;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou *
 * @since 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class WeixinPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeixinPayApplication.class,args);
    }


    @Autowired
    private Environment env;

    //创建队列
    @Bean
    public Queue createQueue(){
        return new Queue(env.getProperty("mq.pay.queue.order"));
    }

    //创建交换机

    @Bean
    public DirectExchange basicExchanage(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.order"));
    }

    //绑定

    @Bean
    public Binding basicBinding(){
       return  BindingBuilder.bind(createQueue()).to(basicExchanage()).with(env.getProperty("mq.pay.routing.key"));
    }



    //创建秒杀队列
    @Bean(name="seckillQueue")
    public Queue createSeckillQueue(){
        return new Queue(env.getProperty("mq.pay.queue.seckillorder"));
    }

    //创建秒杀交换机

    @Bean(name="seckillExchanage")
    public DirectExchange basicSeckillExchanage(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.seckillorder"));
    }

    //绑定秒杀

    @Bean(name="SeckillBinding")
    public Binding basicSeckillBinding(){
        return  BindingBuilder.bind(createSeckillQueue()).to(basicSeckillExchanage()).with(env.getProperty("mq.pay.routing.seckillkey"));
    }
}
