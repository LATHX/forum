package com.forum.utils

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.redis.util.RedisUtil
import com.forum.service.config.GenerateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommonUtil {
    @Autowired
    RedisUtil redisUtil
    @Autowired
    GenerateToken generateToken
    synchronized String generateUUID(){
        return UUID.randomUUID()?.toString()?.replaceAll('-','')
    }
    boolean notEmpty(Object data) {
        if (data == null)
            return false;

        if (data instanceof String) {
            return data.trim().length() > 0
        } else {
            return (data != null && data.toString().trim().length()>0)
        }
    }

    boolean isNotEmpty(Object data) {
        return notEmpty(data)
    }

    boolean isEmpty(Object data) {
        return (! notEmpty(data))
    }

}
