package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.UserMapper
import com.forum.model.dto.MailInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.entity.UserEntity
import com.forum.rabbit.util.RabbitUtil
import com.forum.redis.util.RedisUtil
import com.forum.service.ForgotPasswordService
import com.forum.utils.CommonUtil
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

@Service
class ForgotPasswordServiceImpl implements ForgotPasswordService{
    @Autowired
    MailInfo mailInfo
    @Autowired
    UserMapper userMapper
    @Autowired
    MessageCodeInfo messageCodeInfo
    @Override
    MessageCodeInfo sendForgotMail(HttpServletRequest request, String username) {
        Integer count = userMapper.countNumberByUsername(username)
        if(count == 1){
            String redisText = CommonUtil.generateUUID()+username?.substring(0, username.lastIndexOf('@'))?.reverse()
            String key = Constant.REDIS_FORGOT_PASSWORD_NAME+redisText
            String url = CommonUtil.getURLWithoutContext(request)
            String mailText = url+Constant.REST_PASSWORD_PAGE+'?token='+redisText
            boolean isSetKey = RedisUtil.set(key, username)
            boolean isSetKeyExpired = RedisUtil.expire(key, Constant.FORGOT_PASSWORD_TIMEOUT?.toLong())
            if(!isSetKey || !isSetKeyExpired){
                messageCodeInfo.setMsgInfo(Constant.REGISTER_MAIL_FAIL)
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            }else{
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_OK)
                messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_SUCCESS_MSG)
                mailInfo.setSubject(Constant.FORGOT_MAIL_SUBJECT)
                mailInfo.setText(String.format(Constant.FORGOT_MAIL_TEXT, mailText))
                mailInfo.setSender(Constant.MAIL_ADDRESS)
                mailInfo.setReceiver(username)
                mailInfo.setUseHTTP(true)
                RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_SEND_MAIL, mailInfo)
            }
        }else{
            messageCodeInfo.setMsgInfo(Constant.USERNAME_NOT_EXITS)
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
        }
        return messageCodeInfo
    }

    @Override
    MessageCodeInfo restPassword(HttpServletRequest request, RegisterInfo registerInfo) {
        String key = Constant.REDIS_FORGOT_PASSWORD_NAME + registerInfo.getCode()
        boolean hasKey = RedisUtil?.hasKey(key)
        if(!hasKey){
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(Constant.REST_PASSWORD_TIMEOUT_MSG)
        }else if((registerInfo.getPassword() != registerInfo.getConfirmPassword()) ||(CommonUtil.replaceIllegalCharacter(registerInfo.getPassword())?.length() != registerInfo.getPassword()?.length())){
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(Constant.REGISTER_PASSWORD)
        }else{
            String username = RedisUtil.get(key)
            UserEntity userEntity = userMapper.findUserByUserName(username)
            userEntity.setPassword(DigestUtils?.sha1Hex(registerInfo.getPassword()))
            int updateInt = userMapper.updateByPrimaryKey(userEntity)
            RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_REDIS_DEL, key)
            if(updateInt == 1){
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_OK)
                messageCodeInfo.setMsgInfo('')
            }else{
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
                messageCodeInfo.setMsgInfo(Constant.REST_PASSWORD_FAIL_MSG)
            }
        }
        return messageCodeInfo
    }
}
