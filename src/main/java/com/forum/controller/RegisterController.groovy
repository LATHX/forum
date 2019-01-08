package com.forum.controller

import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.RegisterGroup
import com.forum.model.validationInterface.RegisterMailGroup
import com.forum.service.RegisterService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@Api(value = "用户注册", tags = ['用户注册相关操作'])
@RestController
class RegisterController {
    @Autowired
    RegisterService registerService

    @ApiOperation('用户注册提交')
    @ApiImplicitParam(name = "registerInfo", value = "用户注册信息", dataType = "RegisterInfo")
    @PostMapping('/register')
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

    @ApiOperation('发送用户注册邮件')
    @ApiImplicitParam(name = "registerInfo", value = "用户注册信息", dataType = "RegisterInfo")
    @PostMapping('/send-register-mail')
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
