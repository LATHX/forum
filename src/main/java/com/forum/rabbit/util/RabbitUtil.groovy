package com.forum.rabbit.util

import com.forum.utils.CommonUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class RabbitUtil implements RabbitTemplate.ConfirmCallback, ReturnCallback {
    private final static Logger logger = LoggerFactory.getLogger(RabbitUtil.class)

    private static RabbitTemplate rabbitTemplate

    @Autowired
    void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate
    }

    @PostConstruct
    void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info('Rabbit Message delivery Success ' + correlationData)
        } else {
            logger.error('Rabbit Error>>>>>> Message delivery Fail[Confirm]： ' + cause)
        }

    }

    @Override
    void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error('Rabbit Error>>>>>> Message delivery Fail[return]： ' + message.getMessageProperties()?.getCorrelationId())

    }
    /**
     * 发送消息，不需要实现任何接口，供外部调用
     * @param routingKey
     * @param msg
     */
    static void deliveryMessage(String routingKey, Object msg) {
        logger.info('Sender Date:' + CommonUtil.getCurrentDate());
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        String response = rabbitTemplate.convertSendAndReceive("topicExchange", routingKey, CommonUtil.getBytesFromObject(msg), correlationId).toString()
        logger.info("Consumer response : " + response + " Message Handler Success");
    }

    static void deliveryMessageNotConfirm(String routingKey, Object msg) {
        logger.info('Sender Date:' + CommonUtil.getCurrentDate());
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("topicExchange", routingKey, CommonUtil.getBytesFromObject(msg), correlationId)
        logger.info("Consumer response :  Message Handler Success");
    }

    static void deliveryMessageNotConfirm(String routingKey) {
        deliveryMessageNotConfirm(routingKey, '')
    }
    /**
     * 延时发送，Not Used
     * @param routingKey
     * @param msg
     * @param time
     */

//    private void deliveryDelayMessage(String routingKey, Object msg, int time) {
//        System.out.println('Sender Date:' + CommonUtil.getCurrentDate());
//        String response = rabbitTemplate.convertSendAndReceive("delay_exchange", routingKey, CommonUtil.getBytesFromObject(msg), new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setDelay(30)
//                return message;
//            }
//        });
//        logger.info("Consumer response : " + response + " Message Delay Handler Success");
//    }
//
//    private void deliveryDelayMessage(String routingKey, Object msg) {
//        deliveryDelayMessage(routingKey, msg, 60000)
//    }
}
