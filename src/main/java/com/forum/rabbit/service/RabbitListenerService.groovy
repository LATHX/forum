package com.forum.rabbit.service

import com.forum.global.Constant
import com.forum.redis.util.RedisUtil
import com.forum.utils.CommonUtil
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitListenerService {
    @Autowired
    Constant constant
    @Autowired
    RedisUtil redisUtil

    @RabbitListener(queues = 'secure.token_generate')
    void generateUUID() {
        System.out.println("UUID has Generate once")
        String redisQueueName = constant.UUID_REDIS_QUEUE_NAME
        redisUtil.lSet(redisQueueName, CommonUtil.generateUUID())
    }
}
