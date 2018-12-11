package com.forum.rabbit.service

import com.forum.global.Constant
import com.forum.model.dto.MailInfo
import com.forum.redis.util.RedisUtil
import com.forum.service.MailService
import com.forum.utils.CommonUtil
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitListenerService {
    @Autowired
    MailService mailService
    @RabbitListener(queues = 'secure.token_generate')
    void generateUUID() {
        System.out.println("UUID has Generate once")
        String redisQueueName = Constant.UUID_REDIS_QUEUE_NAME
        RedisUtil.lSet(redisQueueName, CommonUtil.generateUUID())
    }
    @RabbitListener(queues = 'secure.register_mail')
    void sendRegisterMail(byte[] msg){
        println('Register Mail')
        MailInfo mailInfo= (MailInfo)CommonUtil.getObjectFromBytes(msg)
        mailService.sendText(mailInfo)
    }
}
