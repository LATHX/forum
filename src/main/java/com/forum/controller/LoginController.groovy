package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.validationInterface.LoginGroup
import com.forum.service.LoginService
import com.forum.service.security.encrypt.RSACryptoServiceProvider
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
    RSACryptoServiceProvider RSACryptoServiceProvider
    @Autowired
    LoginInfo loginInfo
    @Autowired
    MessageCodeInfo messageCodeInfo
    @Autowired
    LoginService loginService
    @Autowired
    CommonInfo commonInfo

    @PostMapping('/login')
    @ResponseBody
    login(HttpServletRequest request,
          @Validated(value = [LoginGroup.class]) LoginInfo info, BindingResult bindingResult) throws Exception {
        String h = request.getHeader('accept')
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        String code = loginService.validationLoginInfo(request, info)
        if (code == GlobalCode.LOGIN_CODE_FAIL) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_TIMEOUT_MSG)
            commonInfo.setMsg(messageCodeInfo)
        } else if (code == GlobalCode.LOGIN_VERIFY_FAIL) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_VERIFY_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_VERIFY_FAIL_MSG)
            commonInfo.setMsg(messageCodeInfo)
        } else if(code == GlobalCode.ACCOUNT_BLOCK){
            messageCodeInfo.setMsgCode(GlobalCode.ACCOUNT_BLOCK)
            messageCodeInfo?.setMsgInfo(Constant.LOGIN_BLOCK_MSG)
            commonInfo.setMsg(messageCodeInfo)
        }else if (code == GlobalCode.LOGIN_VERIFY_OK) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_VERIFY_OK)
            commonInfo.setMsg(messageCodeInfo)
        }else if(code == GlobalCode.REGISTER_MAIL_FAIL){
            messageCodeInfo.setMsgCode(Constant.REGISTER_MAIL_FAIL)
            messageCodeInfo?.setMsgInfo(Constant.USERNAME_NOT_EXITS)
            commonInfo.setMsg(messageCodeInfo)
        }
        return commonInfo
    }

    @PostMapping('/token')
    @ResponseBody
    token(HttpServletRequest request) throws Exception {
        String code = loginService.getToken(request)
        if (code == (GlobalCode.LOGIN_CODE_FREQUENT)) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FREQUENT)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FREQUENT_MSG)
            loginInfo.setPublicKey(null)
            loginInfo.setToken(null)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        } else if (code == (GlobalCode.LOGIN_CODE_FAIL)) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FREQUENT)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FAIL_MSG)
            loginInfo.setPublicKey(null)
            loginInfo.setToken(null)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        } else {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_OK)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_SUCCESS_MSG)
            loginInfo.setPublicKey(RSACryptoServiceProvider.getPublickey())
            loginInfo.setMsg(messageCodeInfo)
            loginInfo.setToken(code)
        }
        return loginInfo
    }



    @RequestMapping('/logout')
    logout(HttpServletResponse response) throws Exception {
        loginService.logout()
        response.sendRedirect(Constant.LOGIN_PAGE)
    }
}
