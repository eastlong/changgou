package com.itheima.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima.listener *
 * @since 1.0
 */
@Component
@RabbitListener(queues = "queue.message")
public class consumerDelay {

    @RabbitHandler
    public void consumer(String message){
        System.out.println("监听的日志:"+new Date());
        System.out.println(message);
    }
}
