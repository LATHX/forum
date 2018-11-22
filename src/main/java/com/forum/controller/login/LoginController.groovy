package com.forum.controller.login

import com.forum.model.dto.LoginInfo
import com.forum.service.security.encrypt.RSACryptoServiceProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LoginController {
    @Autowired
    RSACryptoServiceProvider RSACryptoServiceProvider
    @Autowired
    LoginInfo loginInfo
    @PostMapping('/token')
    @ResponseBody
    token(@RequestParam String str){
        loginInfo.setPublicKey(RSACryptoServiceProvider.getPublickey())
        return loginInfo
    }
}
