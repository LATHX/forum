package com.forum.controller

import com.alibaba.fastjson.JSONObject
import com.forum.global.Constant
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.utils.CommonUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class RequestLimitExpController {

    @RequestMapping(value = "/limit")
    safeLimitRes(HttpServletRequest request, HttpServletResponse response, Model model, MessageCodeInfo messageCodeInfo, LoginInfo loginInfo, CommonInfo commonInfo) {
        CommonUtil.addCookie(response, 'uuid', CommonUtil.generateUUID())
        String msg = request.getAttribute('msg')
        String messageInfo = Constant.LIMIT_MSG
        if (CommonUtil.isNotEmpty(msg)) {
            messageInfo = msg
        }
        if (CommonUtil.isJsonRequest(request)) {
            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/json;charset=UTF-8")
            PrintWriter printWriter = response.getWriter()
            messageCodeInfo.setMsgInfo(messageInfo)
            loginInfo.setPassword('')
            commonInfo.setMsg(messageCodeInfo)
            printWriter.write(JSONObject.toJSONString(commonInfo))
            printWriter.flush()
        } else {
            model.addAttribute('errorMsg', messageInfo)
            return 'error.html'
        }
    }

    @RequestMapping(value = "/cookie")
    cookieLimitRes(HttpServletRequest request, HttpServletResponse response, Model model, MessageCodeInfo messageCodeInfo, LoginInfo loginInfo, CommonInfo commonInfo) {
        if (CommonUtil.isJsonRequest(request)) {
            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/json;charset=UTF-8")
            PrintWriter printWriter = response.getWriter()
            messageCodeInfo.setMsgInfo('Cookie must be set')
            loginInfo.setPassword('')
            commonInfo.setMsg(messageCodeInfo)
            printWriter.write(JSONObject.toJSONString(commonInfo))
            printWriter.flush()
        } else {
            return 'setcookie.html'
        }
    }


}

