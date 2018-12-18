package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.AreaMapper
import com.forum.mapper.UserMapper
import com.forum.model.dto.MailInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.entity.UserEntity
import com.forum.rabbit.util.RabbitUtil
import com.forum.redis.util.RedisUtil
import com.forum.service.RegisterService
import com.forum.utils.CommonUtil
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

@Service
class RegisterServiceImpl implements RegisterService {
    @Autowired
    MessageCodeInfo messageCodeInfo
    @Autowired
    UserMapper userMapper
    @Autowired
    AreaMapper areaMapper

    @Override
    MessageCodeInfo register(HttpServletRequest request, RegisterInfo registerInfo) {
        String IP = CommonUtil.getRealIP(request)
        String key = Constant.REGISTER_REDIS_MAIL_NAME + IP
        String code = RedisUtil.get(key)
        int usernameCount = userMapper.countNumberByUsername(registerInfo.getUsername())
        if (usernameCount != 0) {
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(Constant.REGISTER_SAME)
        } else if (code?.toLowerCase() != RedisUtil.get(key)?.toString()?.toLowerCase()) {
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(Constant.REGISTER_CODE)
        } else if ((registerInfo.getPassword() != registerInfo.getConfirmPassword()) || (CommonUtil.replaceIllegalCharacter(registerInfo.getPassword())?.length() != registerInfo.getPassword()?.length())) {
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(Constant.REGISTER_PASSWORD)
        } else {
            UserEntity user = new UserEntity()
            user.setUsername(registerInfo.getUsername())
            user.setPassword(DigestUtils.sha1Hex(registerInfo.getPassword()))
            user.setNickname(registerInfo.getNickname())
            user.setSex(registerInfo.getSex()?.toString()?.charAt(0))
            String[] cityArr = registerInfo?.getCity()?.trim()?.split(' ')
            String mergerName = ''
            cityArr?.eachWithIndex { String entry, int i ->
                if (i == 0) {
                    user.setProvince(cityArr[0])
                    mergerName += cityArr[0]
                } else if (i == 1) {
                    user.setCity(cityArr[1])
                    mergerName += cityArr[1]
                } else if (i == 2) {
                    user.setDist(cityArr[2])
                    mergerName += cityArr[2]
                }
            }
            if(mergerName?.trim() != '海外'){
                int areaCount = areaMapper.selectCountByMergeName('中国'+mergerName)
                if(areaCount!=1){
                    messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
                    messageCodeInfo.setMsgInfo(Constant.REGISTER_VERIFY_AREA_FAIL)
                    return messageCodeInfo
                }
            }
//        if(cityArr?.size() == 3){
//                user.setProvince(cityArr[0])
//                user.setCity(cityArr[1])
//                user.setDist(cityArr[2])
//            }else if(cityArr?.size() == 2){
//                user.setProvince(cityArr[0])
//                user.setCity(cityArr[1])
//                user.setDist('')
//            }else if(cityArr?.size() == 1){
//                user.setProvince(cityArr[0])
//                user.setCity('')
//                user.setDist('')
//            }
            int insertInt = userMapper.insertIntoTable(user)
            if (insertInt == 1) {
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_OK)
                messageCodeInfo.setMsgInfo('')
            } else {
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
                messageCodeInfo.setMsgInfo('')
            }
        }
        return messageCodeInfo
    }

    @Override
    boolean registerMail(HttpServletRequest request, RegisterInfo registerInfo) {
        String IP = CommonUtil.getRealIP(request)
        String key = Constant.REGISTER_REDIS_MAIL_NAME + IP
        int alive = Constant.REGISTER_REDIS_TIMEOUT?.toInteger() - (Constant.UUID_REDIS_KEY_TIMEOUT?.toInteger())
        if (CommonUtil.hasRedisKey(key) && RedisUtil.getExpire(key) > alive) {
            return false
        } else {
            String code = CommonUtil.randomCode(6)
            if (CommonUtil.setRedisKeyAndTime(key, code?.toLowerCase(), Constant.REGISTER_REDIS_TIMEOUT?.toLong())) {
                MailInfo mailInfo = new MailInfo()
                mailInfo.setSender(Constant.MAIL_ADDRESS)
                mailInfo.setReceiver(registerInfo.getUsername())
                mailInfo.setSubject(Constant.REGISTER_TITLE)
                mailInfo.setText(String.format(Constant.REGISTER_TEXT, code))
                mailInfo.setUseHTTP(false)
                RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_SEND_MAIL, mailInfo)
                return true
            } else {
                return false
            }
        }

    }
}
