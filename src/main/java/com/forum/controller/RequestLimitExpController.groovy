package com.forum.controller

import com.alibaba.fastjson.JSONObject
import com.forum.global.Constant
import com.forum.model.dto.MessageCodeInfo
import com.forum.utils.CommonUtil
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class RequestLimitExpController {
    @Autowired
    Constant Constant
    @Autowired
    MessageCodeInfo messageCodeInfo
    @RequestMapping(value = "/limit")
     safeLimitRes(HttpServletRequest request, HttpServletResponse response, Model model){
        if(CommonUtil.isJsonRequest(request)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            messageCodeInfo.setMsgInfo(Constant.LIMIT_MSG)
            printWriter.write(JSONObject.toJSONString(messageCodeInfo))
            printWriter.flush();
        }else{
            model.addAttribute('errorMsg',Constant.LIMIT_MSG)
            return 'error.html'
        }
    }


}

