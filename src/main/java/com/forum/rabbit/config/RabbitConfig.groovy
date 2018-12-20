package com.forum.rabbit.config

import com.forum.global.Constant
import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {
    public static final String DEAD_LETTER_EXCHANGE = 'dead_exchange'
    public static final String DEAD_LETTER_ROUTING = 'dead_routing'
    //声明队列
    @Bean
    Queue queue1() {
        return new Queue("hello.queue1", true); // true表示持久化该队列
    }

    @Bean
    Queue queue2() {
        return new Queue("hello.queue2", true);
    }

    //声明交互器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //绑定
    @Bean
    Binding binding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("key.1")
    }

    @Bean
    Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key.#")
    }

    @Bean
    Binding tokenGenerate() {
        return BindingBuilder.bind(new Queue(Constant.MQ_TOKEN_GENERATE, true)).to(topicExchange()).with(Constant.MQ_TOKEN_GENERATE)
    }

    @Bean
    Binding registerMail() {
        return BindingBuilder.bind(new Queue(Constant.MQ_SEND_MAIL, true)).to(topicExchange()).with(Constant.MQ_SEND_MAIL)
    }

    @Bean
    Binding delRedisKey() {
        return BindingBuilder.bind(new Queue(Constant.MQ_REDIS_DEL, true)).to(topicExchange()).with(Constant.MQ_REDIS_DEL)
    }

    @Bean
    Binding delRedisUserSession() {
        return BindingBuilder.bind(new Queue(Constant.DEL_REDIS_USER_SESSION, true)).to(topicExchange()).with(Constant.DEL_REDIS_USER_SESSION)
    }

    @Bean
    Queue maintainQueue() {
        Map<String, Object> args = new HashMap<>()
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING)
        return new Queue(DEAD_LETTER_ROUTING, true, false, false, args)
    }

    @Bean
    Binding maintainBinding() {
        return BindingBuilder.bind(maintainQueue()).to(DirectExchange.DEFAULT)
                .with(DEAD_LETTER_ROUTING)
    }

    @Bean
    Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_EXCHANGE)
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false)
    }

    @Bean
    Binding deadLetterBindding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_EXCHANGE)
    }
}
