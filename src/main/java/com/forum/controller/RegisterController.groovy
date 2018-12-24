package com.forum.controller

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
    RegisterService registerService

    @PostMapping('/register')
    @ResponseBody
    register(HttpServletRequest request,
             @Validated(value = [RegisterGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) throws Exception {
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        } else {
            messageCodeInfo = registerService.register(request, registerInfo, messageCodeInfo)
        }
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @PostMapping('/send-register-mail')
    @ResponseBody
    registerMail(HttpServletRequest request,
                 @Validated(value = [RegisterMailGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) throws Exception {
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        } else {
            messageCodeInfo = registerService.registerMail(request, registerInfo, messageCodeInfo)
        }
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }
}
