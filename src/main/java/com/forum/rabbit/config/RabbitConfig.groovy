package com.forum.rabbit.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig  {
    public static final String delay_queue = 'delay_queue'
    public static final String DEAD_LETTER_EXCHANGE = 'dead_exchange'
    public static final String DEAD_LETTER_ROUTING = 'dead_routing'
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

    @Bean
    public Queue maintainQueue() {
        Map<String,Object> args=new HashMap<>();
        // 设置该Queue的死信的信箱
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 设置死信routingKey
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING);
        return new Queue(DEAD_LETTER_ROUTING,true,false,false,args);
    }

    @Bean
    public Binding maintainBinding() {
        return BindingBuilder.bind(maintainQueue()).to(DirectExchange.DEFAULT)
                .with(DEAD_LETTER_ROUTING);
    }
    @Bean
    public Queue deadLetterQueue(){
        return new Queue(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Binding deadLetterBindding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_EXCHANGE);
    }
}
