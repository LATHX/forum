package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.validationInterface.LoginGroup
import com.forum.service.security.encrypt.RSACryptoServiceProvider
import com.forum.service.service.LoginService
import com.forum.utils.CommonUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    Constant Constant
    @Autowired
    GlobalCode GlobalCode
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class)

    @RequestMapping('main')
    main() {
        return 'index.html'
    }

    @PostMapping('/login')
    @ResponseBody
    login(HttpServletRequest request,
          @Validated(value = [LoginGroup.class]) LoginInfo info, BindingResult bindingResult) throws Exception {
        String h = request.getHeader('accept')
        if (bindingResult?.hasErrors()) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FAIL)
            messageCodeInfo.setMsgInfo(bindingResult?.getFieldError()?.getDefaultMessage())
            loginInfo.setMsg(messageCodeInfo)
            return loginInfo
        }
        String code = loginService.validationLoginInfo(CommonUtil.getRealIP(request), info)
        if (code == GlobalCode.LOGIN_CODE_FAIL) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_TIMEOUT_MSG)
            loginInfo.setMsg(messageCodeInfo)
        } else if (code == GlobalCode.LOGIN_VERIFY_FAIL) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_VERIFY_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_VERIFY_FAIL_MSG)
            loginInfo.setMsg(messageCodeInfo)
        } else if (code == GlobalCode.LOGIN_VERIFY_OK) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_VERIFY_OK)
            loginInfo.setMsg(messageCodeInfo)
        }
        return loginInfo
    }

    @PostMapping('/token')
    @ResponseBody
    token(HttpServletRequest request) throws Exception {
        String code = loginService.getToken(CommonUtil.getRealIP(request))
        if (code == (GlobalCode.LOGIN_CODE_FREQUENT)) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FREQUENT)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FREQUENT_MSG)
            loginInfo.setPublicKey(null)
            loginInfo.setToken(null)
            loginInfo.setMsg(messageCodeInfo)
        } else if (code == (GlobalCode.LOGIN_CODE_FAIL)) {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FREQUENT)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FAIL_MSG)
            loginInfo.setPublicKey(null)
            loginInfo.setToken(null)
            loginInfo.setMsg(messageCodeInfo)
        } else {
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_OK)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_SUCCESS_MSG)
            loginInfo.setPublicKey(RSACryptoServiceProvider.getPublickey())
            loginInfo.setMsg(messageCodeInfo)
            loginInfo.setToken(code)
        }
        return loginInfo
    }

    @RequestMapping('/loginpage')
    loginPage(HttpServletResponse response) throws Exception {
        response.sendRedirect(Constant.LOGIN_PAGE)
    }

    @RequestMapping('/logout')
    logout(HttpServletResponse response) throws Exception {
        loginService.logout()
        response.sendRedirect(Constant.LOGIN_PAGE)
    }
}
