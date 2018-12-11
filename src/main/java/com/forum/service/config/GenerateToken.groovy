package com.forum.service.config

import com.forum.global.Constant
import com.forum.redis.util.RedisUtil
import com.forum.utils.CommonUtil
import org.springframework.stereotype.Component

@Component
class GenerateToken {
    void generateUUIDQueue(){
        int current_size = 0
        String redisQueueName = Constant.UUID_REDIS_QUEUE_NAME
        if(RedisUtil.hasKey(redisQueueName) == false){
            RedisUtil.lSet(redisQueueName, generateUUIDList(current_size))
        }else{
            current_size = RedisUtil.lGetListSize(redisQueueName)
            generateUUIDList(current_size).each {
                RedisUtil.lSet(redisQueueName, it)
            }
        }

    }

    List<String> generateUUIDList(int current_size){
        List<String> list = new ArrayList<String>()
        while(current_size <= Constant.UUID_REDIS_CACHE_SIZE){
            list.add(CommonUtil.generateUUID())
            current_size ++;
        }
        return list
    }


}
