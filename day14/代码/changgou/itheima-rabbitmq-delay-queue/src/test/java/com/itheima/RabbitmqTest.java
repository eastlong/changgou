package com.itheima;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.itheima *
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void send(){
        System.out.println( "send:"+new Date());
        rabbitTemplate.convertAndSend("queue.message.delay", (Object) "hello world", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置过期时间
                message.getMessageProperties().setExpiration("10000");//10 S
                return message;
            }
        });


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
