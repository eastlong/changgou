package com.itheima;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima *
 * @since 1.0
 */
@SpringBootApplication
public class RabbitMQDelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQDelayApplication.class,args);
    }

    //创建队列 queue1  (用于生产者发送的)
    // ---->使用默认的交换机 使用默认的路由key   (message)---->死信队列---> 参数指定的exchange 和路由key中

    //----> queue2(绑定到参数指定的exchange 和路由key中)

    @Bean
    public Queue createQueue1(){
        return QueueBuilder.durable("queue.message.delay")
                .withArgument("x-dead-letter-exchange", "dlx.exchange")
                .withArgument("x-dead-letter-routing-key", "queue.message")
                .build();
    }


    //创建队列 queue2  (用于消费者消费的)

    @Bean
    public Queue creatreQueue2(){
        return new Queue("queue.message");
    }


    //创建交换机

    @Bean
    public DirectExchange CreatedirectExchange(){
        return new DirectExchange("dlx.exchange");
    }

    //创建binding

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(creatreQueue2()).to(CreatedirectExchange()).with("queue.message");
    }










}
