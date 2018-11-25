package com.forum.controller.login

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.service.security.encrypt.RSACryptoServiceProvider
import com.forum.service.service.LoginService
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

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
    CommonUtil util

    @PostMapping('/token')
    @ResponseBody
    token(HttpServletRequest request){String s = util.getRealIP(request)
        String code = loginService.getToken(util.getRealIP(request))
        if(code == (GlobalCode.LOGIN_CODE_FREQUENT)){
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FREQUENT)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FREQUENT_MSG)
            loginInfo.setMsg(messageCodeInfo)
        }else if(code == (GlobalCode.LOGIN_CODE_FAIL)){
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_FREQUENT)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_FAIL_MSG)
            loginInfo.setMsg(messageCodeInfo)
        }else{
            messageCodeInfo.setMsgCode(GlobalCode.LOGIN_CODE_OK)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_CODE_SUCCESS_MSG)
            loginInfo.setPublicKey(RSACryptoServiceProvider.getPublickey())
            loginInfo.setMsg(messageCodeInfo)
            loginInfo.setToken(code)
        }
        return loginInfo
    }
}
