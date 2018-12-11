package com.forum.service.impl

import com.forum.global.Constant
import com.forum.model.dto.MailInfo
import com.forum.model.dto.RegisterInfo
import com.forum.rabbit.util.RabbitUtil
import com.forum.service.RegisterService
import com.forum.utils.CommonUtil
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest
@Service
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
            String code = CommonUtil.randomCode(6)
            if(CommonUtil.setRedisKeyAndTime(key, code?.toLowerCase(), Constant.REGISTER_REDIS_TIMEOUT?.toLong())){
                MailInfo mailInfo = new MailInfo()
                mailInfo.setSender(Constant.MAIL_ADDRESS)
                mailInfo.setReceiver(registerInfo.getUsername())
                mailInfo.setSubject(Constant.REGISTER_TITLE)
                mailInfo.setText(Constant.REGISTER_TEXT+code)
                mailInfo.setUseHTTP(false)
                RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_REGISTER_MAIL, mailInfo)
                return true
            }else{
                return false
            }
        }

    }
}
