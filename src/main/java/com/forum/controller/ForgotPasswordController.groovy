package com.forum.controller

import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.ForgotPasswordGroup
import com.forum.model.validationInterface.RestPasswordGroup
import com.forum.service.ForgotPasswordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

@Controller
class ForgotPasswordController {
    @Autowired
    ForgotPasswordService forgotPasswordService


    @RequestMapping('/rest-password')
    @ResponseBody
    restPassword(HttpServletRequest request,
                 @Validated(value = [RestPasswordGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        } else {
            messageCodeInfo = forgotPasswordService.restPassword(request, registerInfo, messageCodeInfo)
        }
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @RequestMapping('/forgot-mail')
    @ResponseBody
    forgotMail(HttpServletRequest request,
               @Validated(value = [ForgotPasswordGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        } else {
            messageCodeInfo = forgotPasswordService.sendForgotMail(request, registerInfo.getUsername(), messageCodeInfo)
        }
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

}
