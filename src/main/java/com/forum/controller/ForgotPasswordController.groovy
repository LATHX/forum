package com.forum.controller

import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.ForgotPasswordGroup
import com.forum.model.validationInterface.RestPasswordGroup
import com.forum.service.ForgotPasswordService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@Api(value = "用户重置密码", tags = [ '用户重置密码相关操作' ])
@RestController
class ForgotPasswordController {
    @Autowired
    ForgotPasswordService forgotPasswordService

    @ApiOperation('用户重置密码')
    @ApiImplicitParam(name = "registerInfo", value = "用户重置密码信息", dataType = "RegisterInfo")
    @PostMapping('/rest-password')
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

    @ApiOperation('发送用户重置密码邮件')
    @ApiImplicitParam(name = "registerInfo", value = "用户重置密码信息", dataType = "RegisterInfo")
    @PostMapping('/forgot-mail')
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
