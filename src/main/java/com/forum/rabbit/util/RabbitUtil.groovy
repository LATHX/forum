package com.forum.rabbit.util

import com.forum.utils.CommonUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class RabbitUtil implements RabbitTemplate.ConfirmCallback, ReturnCallback {
    private final static Logger logger = LoggerFactory.getLogger(RabbitUtil.class)
    @Autowired
    RabbitTemplate rabbitTemplate

    @PostConstruct
    void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info('Rabbit Message delivery Success' + correlationData)
        } else {
            logger.error('Rabbit Error>>>>>> Message delivery Fail[Confirm]： ' + cause)
        }

    }

    @Override
    void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error('Rabbit Error>>>>>> Message delivery Fail[return]： ' + message.getMessageProperties()?.getCorrelationId())

    }
    //发送消息，不需要实现任何接口，供外部调用。
    void deliveryMessage(String routingKey, Object msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        String response = rabbitTemplate.convertSendAndReceive("topicExchange", routingKey, CommonUtil.getBytesFromObject(msg), correlationId).toString()
        logger.info("Consumer response : " + response + " Message Handler Success");
    }

    void deliveryDelayMessage(String routingKey, Object msg, int time) {
        String response = rabbitTemplate.convertAndSend("delay_exchange", routingKey, CommonUtil.getBytesFromObject(msg), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", time);
                return message;
            }
        });
        logger.info("Consumer response : " + response + " Message Handler Success");
    }

    void deliveryDelayMessage(String routingKey, Object msg) {
        deliveryDelayMessage(routingKey, msg, 60000)
    }
}
