package com.forum.service.service

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.redis.util.RedisUtil
import com.forum.service.config.GenerateToken
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoginService {
    @Autowired
    RedisUtil redisUtil
    @Autowired
    GenerateToken generateToken
    @Autowired
    CommonUtil util
    String getToken(String ip){
        if(redisUtil.hasKey(Constant.UUID_REDIS_QUEUE_NAME) == false) {
            generateToken.generateUUIDQuence()
        }
        boolean hasKey = redisUtil.hasKey(ip)
        if(hasKey && redisUtil.getExpire(ip) == 0){
            redisUtil.del(ip)
        }else if(hasKey){
            return GlobalCode.LOGIN_CODE_FREQUENT
        }
        String str = redisUtil.leftPopSet(Constant.UUID_REDIS_QUEUE_NAME)
        if(util.isEmpty(str)){
            return GlobalCode.LOGIN_CODE_FAIL
        }
        boolean keyFlag = redisUtil.set(ip, str)
        boolean expireFlag = redisUtil.expire(ip, Constant.UUID_REDIS_KEY_TIMEOUT?.toLong())
        if(keyFlag == false || expireFlag == false) {
            return GlobalCode.LOGIN_CODE_FAIL
        }
        return str
    }
}
