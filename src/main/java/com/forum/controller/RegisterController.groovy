package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.RegisterGroup
import com.forum.model.validationInterface.RegisterMailGroup
import com.forum.service.RegisterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

@Controller
class RegisterController {
    @Autowired
    MessageCodeInfo messageCodeInfo
    @Autowired
    CommonInfo info
    @Autowired
    RegisterService registerService
    @PostMapping('/register')
    @ResponseBody
     register(HttpServletRequest request, @Validated(value = [RegisterGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult) throws Exception {
        if(bindingResult?.hasErrors()){
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        }else{
            messageCodeInfo = registerService.register(request, registerInfo)
        }
        info.setMsg(messageCodeInfo)
        return info
    }
    @PostMapping('/send-register-mail')
    @ResponseBody
    registerMail(HttpServletRequest request, @Validated(value = [RegisterMailGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult) throws Exception {
        if(bindingResult?.hasErrors()){
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        }else{
            if(registerService.registerMail(request, registerInfo)){
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_OK)
                messageCodeInfo.setMsgInfo('')
            }else{
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
                messageCodeInfo.setMsgInfo(Constant.REGISTER_MAIL_FAIL)
            }
        }
        info.setMsg(messageCodeInfo)
        return info
    }
}
