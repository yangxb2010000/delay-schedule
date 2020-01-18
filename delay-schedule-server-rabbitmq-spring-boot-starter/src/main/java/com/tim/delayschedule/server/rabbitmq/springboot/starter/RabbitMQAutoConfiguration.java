package com.tim.delayschedule.server.rabbitmq.springboot.starter;

import com.tim.delayschedule.server.rabbitmq.springboot.starter.rabbitmq.RabbitMQConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: xiaobing
 * Date: 2020/1/18 21:37
 * Description:
 */
@Configuration
public class RabbitMQAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RabbitMQConsumer rabbitMQConsumer() {
        return new RabbitMQConsumer();
    }
}