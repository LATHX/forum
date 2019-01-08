package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.SessionEntity
import com.forum.model.validationInterface.LoginGroup
import com.forum.service.LoginService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Api(value = "用户登录", tags = [ '用户登录相关操作' ])
@RestController
class LoginController {
    @Autowired
    LoginService loginService

    @ApiOperation('用户登录')
    @ApiImplicitParam(name = "info", value = "用户登录信息", dataType = "LoginInfo")
    @PostMapping('/login')
    login(HttpServletRequest request,
          @Validated(value = [LoginGroup.class]) LoginInfo info, BindingResult bindingResult, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo, SessionEntity sessionEntity) throws Exception {
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        } else {
            messageCodeInfo = loginService.validationLoginInfo(request, info, messageCodeInfo, sessionEntity)
        }
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('用户登录请求Token')
    @ApiImplicitParam(name = "info", value = "用户登录信息", dataType = "LoginInfo")
    @PostMapping('/token')
    token(HttpServletRequest request, LoginInfo loginInfo, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) throws Exception {
        messageCodeInfo = loginService.getToken(request, loginInfo, messageCodeInfo)
        if (messageCodeInfo.getMsgCode() != GlobalCode.REFERENCE_SUCCESS) {
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        } else {
            return loginInfo
        }
    }


    @ApiOperation('用户退出登录')
    @GetMapping('/logout')
    logout(HttpServletResponse response) throws Exception {
        loginService.logout()
        response.sendRedirect(Constant.LOGIN_PAGE)
    }
}
