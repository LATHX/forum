package com.forum.service.service

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.LoginInfo
import com.forum.redis.util.RedisUtil
import com.forum.service.config.GenerateToken
import com.forum.service.security.encrypt.RSACryptoServiceProvider
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoginService {
    @Autowired
    RSACryptoServiceProvider RSACryptoServiceProvider
    @Autowired
    RedisUtil redisUtil
    @Autowired
    GenerateToken generateToken
    @Autowired
    CommonUtil util
    @Autowired
    Constant Constant
    @Autowired
    GlobalCode GlobalCode

    String validationLoginInfo(String ip, LoginInfo loginInfo){
        String a = RSACryptoServiceProvider.decrypt(loginInfo.getPassword())
        loginInfo.setPassword(RSACryptoServiceProvider.decrypt(loginInfo.getPassword()))
        boolean hasKey = redisUtil.hasKey(ip)
        if(!hasKey){
            return GlobalCode.LOGIN_CODE_FAIL
        }else{
            String token = redisUtil.get(ip)
            if(token != loginInfo.getToken()){
                return GlobalCode.LOGIN_CODE_FAIL
            }
            redisUtil.del(ip)
        }
        if(loginInfo.username == '123' && loginInfo.password=='202cb962ac59075b964b07152d234b70'){
            return GlobalCode.LOGIN_VERIFY_OK
        }
        return GlobalCode.LOGIN_VERIFY_FAIL
    }

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
