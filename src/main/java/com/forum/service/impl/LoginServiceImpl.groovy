package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.LoginInfo
import com.forum.rabbit.util.RabbitUtil
import com.forum.redis.util.RedisUtil
import com.forum.service.LoginService
import com.forum.service.config.GenerateToken
import com.forum.service.security.encrypt.RSACryptoServiceProvider
import com.forum.utils.CommonUtil
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl implements LoginService {
    @Autowired
    RSACryptoServiceProvider RSACryptoServiceProvider
    @Autowired
    RedisUtil redisUtil
    @Autowired
    RabbitUtil rabbitUtil;
    @Autowired
    GenerateToken generateToken
    @Autowired
    Constant Constant
    @Autowired
    GlobalCode GlobalCode

    @Override
    String validationLoginInfo(String ip, LoginInfo loginInfo) {
        loginInfo.setPassword(RSACryptoServiceProvider.decrypt(loginInfo.getPassword()).replaceFirst(loginInfo.token, ''))
        boolean hasKey = redisUtil.hasKey(ip)
        if (!hasKey) {
            return GlobalCode.LOGIN_CODE_FAIL
        } else {
            String token = redisUtil.get(ip)
            if (token != loginInfo.getToken()) {
                return GlobalCode.LOGIN_CODE_FAIL
            }
            redisUtil.del(ip)
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword(), false);
        try {
            subject.login(token);
            return GlobalCode.LOGIN_VERIFY_OK
        } catch (Exception e) {
            e.printStackTrace()
            return GlobalCode.LOGIN_VERIFY_FAIL
        }
        return GlobalCode.LOGIN_VERIFY_FAIL
    }

    @Override
    String getToken(String ip) {
        if (redisUtil.hasKey(Constant.UUID_REDIS_QUEUE_NAME) == false) {
            generateToken.generateUUIDQueue()
        }
        boolean hasKey = redisUtil.hasKey(ip)
        if (hasKey && redisUtil.getExpire(ip) == 0) {
            redisUtil.del(ip)
        } else if (hasKey) {
            return GlobalCode.LOGIN_CODE_FREQUENT
        }
        String str = redisUtil.leftPopSet(Constant.UUID_REDIS_QUEUE_NAME)
        if (CommonUtil.isEmpty(str)) {
            return GlobalCode.LOGIN_CODE_FAIL
        }
        rabbitUtil.deliveryMessageNotConfirm(Constant.MQ_TOKEN_GENERATE)
        boolean keyFlag = redisUtil.set(ip, str)
        boolean expireFlag = redisUtil.expire(ip, Constant.UUID_REDIS_KEY_TIMEOUT?.toLong())
        if (keyFlag == false || expireFlag == false) {
            return GlobalCode.LOGIN_CODE_FAIL
        }
        return str
    }

    @Override
    void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
