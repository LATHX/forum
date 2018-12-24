package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.SessionEntity
import com.forum.model.validationInterface.LoginGroup
import com.forum.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class LoginController {
    @Autowired
    LoginService loginService


    @PostMapping('/login')
    @ResponseBody
    login(HttpServletRequest request,
          @Validated(value = [LoginGroup.class]) LoginInfo info, BindingResult bindingResult, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo, SessionEntity sessionEntity) throws Exception {
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
        } else {
            messageCodeInfo = loginService.validationLoginInfo(request, info, messageCodeInfo, commonInfo, sessionEntity)
        }
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @PostMapping('/token')
    @ResponseBody
    token(HttpServletRequest request, LoginInfo loginInfo, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) throws Exception {
        messageCodeInfo = loginService.getToken(request, loginInfo, messageCodeInfo, commonInfo)
        if (messageCodeInfo.getMsgCode() != GlobalCode.REFERENCE_SUCCESS) {
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        } else {
            return loginInfo
        }
    }


    @RequestMapping('/logout')
    logout(HttpServletResponse response) throws Exception {
        loginService.logout()
        response.sendRedirect(Constant.LOGIN_PAGE)
    }
}
