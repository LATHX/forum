package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.RegisterGroup
import com.forum.model.validationInterface.RegisterMailGroup
import com.forum.service.RegisterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

@Controller
class RegisterController {
    @Autowired
    MessageCodeInfo messageCodeInfo
    @Autowired
    RegisterInfo registerInfo
    @Autowired
    RegisterService registerService
    @RequestMapping('/register')
     register(@Validated(value = [RegisterGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult) throws Exception {
        if(bindingResult?.hasErrors()){

        }
    }
    @RequestMapping('/send-register-mail')
    @ResponseBody
    registerMail(HttpServletRequest request, @Validated(value = [RegisterMailGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult) throws Exception {
        if(bindingResult?.hasErrors()){
            messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
            registerInfo.setMsg(messageCodeInfo)
            return registerInfo
        }else{
            if(registerService.registerMail(request, registerInfo)){
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_OK)
                messageCodeInfo.setMsgInfo('')
            }else{
                messageCodeInfo.setMsgCode(GlobalCode.REGISTER_MAIL_FAIL)
                messageCodeInfo.setMsgInfo(Constant.REGISTER_MAIL_FAIL)
            }
        }
        registerInfo.setMsg(messageCodeInfo)
        return registerInfo
    }
}
