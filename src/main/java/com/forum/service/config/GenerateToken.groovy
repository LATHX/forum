package com.forum.service.config

import com.forum.global.Constant
import com.forum.redis.util.RedisUtil
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GenerateToken {
    @Autowired
    RedisUtil redisUtil
    @Autowired
    CommonUtil commonUtil
    void generateUUIDQuence(){
        int current_size = 0
        if(redisUtil.hasKey(Constant.UUID_REDIS_QUEUE_NAME) == false){
            redisUtil.lSet(Constant.UUID_REDIS_QUEUE_NAME, generateUUIDList(current_size))
        }else{
            current_size = redisUtil.lGetListSize(Constant.UUID_REDIS_QUEUE_NAME)
            generateUUIDList(current_size).each {
                redisUtil.lSet(Constant.UUID_REDIS_QUEUE_NAME, it)
            }
        }

    }

    List<String> generateUUIDList(int current_size){
        List<String> list = new ArrayList<String>()
        while(current_size <= Constant.UUID_REDIS_CACHE_SIZE){
            list.add(commonUtil.generateUUID())
            current_size ++;
        }
        return list
    }

}
