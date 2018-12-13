package com.forum.controller

import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.service.ForgotPasswordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

@Controller
class ForgotPassWordController {
    @Autowired
    ForgotPasswordService forgotPasswordService
    @Autowired
    CommonInfo commonInfo
    @RequestMapping('/forgot-mail')
    @ResponseBody
    forgotMail(HttpServletRequest request, String username){
        MessageCodeInfo messageCodeInfo = forgotPasswordService.sendForgotMail(request, username)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

}
