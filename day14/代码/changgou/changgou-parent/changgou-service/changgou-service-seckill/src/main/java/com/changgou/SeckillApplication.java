package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@MapperScan(basePackages = {"com.changgou.seckill.dao"})
@EnableScheduling//启用任务    使得 任务注解能生效

@EnableAsync//开始多线程的支持()
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class,args);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //采用普通的key 为 字符串
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }
}
