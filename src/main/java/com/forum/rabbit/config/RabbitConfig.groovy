package com.forum.rabbit.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig  {
    public static final String delay_queue = 'delay_queue'
    //声明队列
    @Bean
    public Queue queue1() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", (1000*60*60))
        return new Queue("hello.queue1", true,false, false, args); // true表示持久化该队列
    }

    @Bean
    public Queue queue2() {
        return new Queue("hello.queue2", true);
    }
    /**
     * 创建延迟队列
     * @return Queue
     */
    @Bean
    Queue delayQueue(){
        return new Queue(delay_queue, true)
    }

    //声明交互器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }
    /**
     * 自定义延迟消息交互器
     * @return CustomExchange
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delay_exchange", "x-delayed-message",true, false,args);
    }

    //绑定
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("key.1")
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key.#");
    }
    /**
     * 绑定延迟交互器与队列
     * @return
     */
    @Bean
    Binding bindingDelay(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay_queue").noargs();
    }


}
