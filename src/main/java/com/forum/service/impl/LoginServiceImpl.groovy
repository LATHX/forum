package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.UserMapper
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
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
    @Autowired
    MessageCodeInfo messageCodeInfo

    @Override
    MessageCodeInfo validationLoginInfo(HttpServletRequest request, LoginInfo loginInfo) {
        String customCookie = CommonUtil.getCookies(request, 'custom.name')?.toString()
        String key = CommonUtil.getRealIP(request).concat(customCookie)
        boolean isRememberMe = true
        loginInfo.setPassword(RSACryptoServiceProvider.decrypt(loginInfo.getPassword()).replaceFirst(loginInfo.token, ''))
        boolean hasKey = RedisUtil.hasKey(key)
        if (CommonUtil.isEmpty(loginInfo.rememberMe) || loginInfo.rememberMe?.trim() == 'false') {
            isRememberMe = false
        }
        if (!hasKey) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_TIMEOUT_MSG)
            return messageCodeInfo
        } else {
            String token = RedisUtil.get(key)
            if (token != loginInfo.getToken()) {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
                messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_TIMEOUT_MSG)
                return messageCodeInfo
            }
            RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_REDIS_DEL, key)
        }
        Integer hasAccount = userMapper.countNumberByUsername(loginInfo.getUsername())
        if(hasAccount != 1){
            messageCodeInfo.setMsgCode(Constant.REGISTER_MAIL_FAIL)
            messageCodeInfo?.setMsgInfo(Constant.USERNAME_NOT_EXITS)
            return messageCodeInfo
        }
        boolean isEnable = userMapper.isAccountBlock(loginInfo.getUsername())
        if (!isEnable) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_BLOCK_MSG)
            return messageCodeInfo
        }

        Subject subject = SecurityUtils.getSubject()
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), DigestUtils?.sha1Hex(loginInfo.getPassword()), isRememberMe)
        try {
            subject.login(token)
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            messageCodeInfo.setMsgInfo('')
            return messageCodeInfo
        } catch (Exception e) {
            messageCodeInfo.setMsgCode(Constant.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_VERIFY_FAIL_MSG)
            return messageCodeInfo
        }
    }

    @Override
    MessageCodeInfo getToken(HttpServletRequest request, LoginInfo loginInfo) {
        String customCookie = CommonUtil?.getCookies(request, 'custom.name')?.toString()
        String ip = CommonUtil.getRealIP(request).concat(customCookie)
        if (!RedisUtil.hasKey(Constant.UUID_REDIS_QUEUE_NAME)) {
            generateToken.generateUUIDQueue()
        }
        boolean hasKey = RedisUtil.hasKey(ip)
        if (hasKey && RedisUtil.getExpire(ip) == 0) {
            RedisUtil.del(ip)
        } else if (hasKey) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FREQUENT_MSG)
            return messageCodeInfo
        }
        String str = RedisUtil.leftPopSet(Constant.UUID_REDIS_QUEUE_NAME)
        if (CommonUtil.isEmpty(str)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FAIL_MSG)
            return messageCodeInfo
        }
        RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_TOKEN_GENERATE)
        boolean keyFlag = RedisUtil.set(ip, str)
        boolean expireFlag = RedisUtil.expire(ip, Constant.UUID_REDIS_KEY_TIMEOUT?.toLong())
        if (!keyFlag || !expireFlag) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FAIL_MSG)
            return messageCodeInfo
        }
        messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_SUCCESS_MSG)
        loginInfo.setPublicKey(RSACryptoServiceProvider.getPublickey())
        loginInfo.setMsg(messageCodeInfo)
        loginInfo.setToken(str)
        return messageCodeInfo
    }

    @Override
    void logout() {
        Subject subject = SecurityUtils.getSubject()
        subject.logout()
    }
}
