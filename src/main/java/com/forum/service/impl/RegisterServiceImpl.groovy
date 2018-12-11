package com.forum.service.impl

import com.forum.global.Constant
import com.forum.model.dto.MailInfo
import com.forum.model.dto.RegisterInfo
import com.forum.rabbit.util.RabbitUtil
import com.forum.service.RegisterService
import com.forum.utils.CommonUtil

import javax.servlet.http.HttpServletRequest

class RegisterServiceImpl implements RegisterService{
    @Override
    boolean register(HttpServletRequest request, RegisterInfo registerInfo) {

    }

    @Override
    boolean registerMail(HttpServletRequest request, RegisterInfo registerInfo) {
        String IP = CommonUtil.getRealIP(request)
        String key = Constant.REGISTER_REDIS_MAIL_NAME+IP
        if(CommonUtil.hasRedisKey(key)){
            return false
        }else{
            if(CommonUtil.setRedisKeyAndTime(key, CommonUtil.randomCode(6), Constant.REGISTER_REDIS_TIMEOUT?.toLong())){
                MailInfo mailInfo = new MailInfo()
                mailInfo.setSender(Constant.MQ_REIGSTER_MAIL)
                mailInfo.setReceiver(registerInfo.getUsername())
                mailInfo.setSubject()
                mailInfo.setUseHTTP(false)
                RabbitUtil.deliveryMessageNotConfirm()
                return true
            }else{
                return false
            }
        }

    }
}
