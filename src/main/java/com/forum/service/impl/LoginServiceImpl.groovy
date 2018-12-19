package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.UserMapper
import com.forum.model.dto.LoginInfo
import com.forum.rabbit.util.RabbitUtil
import com.forum.redis.util.RedisUtil
import com.forum.service.LoginService
import com.forum.service.config.GenerateToken
import com.forum.service.security.encrypt.RSACryptoServiceProvider
import com.forum.utils.CommonUtil
import org.apache.commons.codec.digest.DigestUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

@Service
class LoginServiceImpl implements LoginService {
    @Autowired
    RSACryptoServiceProvider RSACryptoServiceProvider
    @Autowired
    GenerateToken generateToken
    @Autowired
    UserMapper userMapper

    @Override
    String validationLoginInfo(HttpServletRequest request, LoginInfo loginInfo) {
        String uuidCookie = CommonUtil.getCookies(request, 'uuid')
        String ip = CommonUtil.getRealIP(request).concat(uuidCookie)
        boolean isRememberMe = true
        loginInfo.setPassword(RSACryptoServiceProvider.decrypt(loginInfo.getPassword()).replaceFirst(loginInfo.token, ''))
        boolean hasKey = RedisUtil.hasKey(ip)
        if (CommonUtil.isEmpty(loginInfo.rememberMe) || loginInfo.rememberMe == 'false') {
            isRememberMe = false
        }
        if (!hasKey) {
            return GlobalCode.LOGIN_CODE_FAIL
        } else {
            String token = RedisUtil.get(ip)
            if (token != loginInfo.getToken()) {
                return GlobalCode.LOGIN_CODE_FAIL
            }
            RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_REDIS_DEL, ip)
        }
        Integer hasAccount = userMapper.countNumberByUsername(loginInfo.getUsername())
        if(hasAccount != 1){
            return GlobalCode.REGISTER_MAIL_FAIL
        }
        boolean isEnable = userMapper.isAccountBlock(loginInfo.getUsername())
        if (!isEnable) {
            return GlobalCode.ACCOUNT_BLOCK
        }

        Subject subject = SecurityUtils.getSubject()
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), DigestUtils?.sha1Hex(loginInfo.getPassword()), isRememberMe)
        try {
            subject.login(token)
            return GlobalCode.LOGIN_VERIFY_OK
        } catch (Exception e) {
            return GlobalCode.LOGIN_VERIFY_FAIL
        }
    }

    @Override
    String getToken(HttpServletRequest request) {
        String uuidCookie = CommonUtil.getCookies(request, 'uuid')
        String ip = CommonUtil.getRealIP(request).concat(uuidCookie)
        if (!RedisUtil.hasKey(Constant.UUID_REDIS_QUEUE_NAME)) {
            generateToken.generateUUIDQueue()
        }
        boolean hasKey = RedisUtil.hasKey(ip)
        if (hasKey && RedisUtil.getExpire(ip) == 0) {
            RedisUtil.del(ip)
        } else if (hasKey) {
            return GlobalCode.LOGIN_CODE_FREQUENT
        }
        String str = RedisUtil.leftPopSet(Constant.UUID_REDIS_QUEUE_NAME)
        if (CommonUtil.isEmpty(str)) {
            return GlobalCode.LOGIN_CODE_FAIL
        }
        RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_TOKEN_GENERATE)
        boolean keyFlag = RedisUtil.set(ip, str)
        boolean expireFlag = RedisUtil.expire(ip, Constant.UUID_REDIS_KEY_TIMEOUT?.toLong())
        if (!keyFlag || !expireFlag) {
            return GlobalCode.LOGIN_CODE_FAIL
        }
        return str
    }

    @Override
    void logout() {
        Subject subject = SecurityUtils.getSubject()
        subject.logout()
    }
}
