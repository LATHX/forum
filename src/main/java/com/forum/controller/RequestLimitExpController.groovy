package com.forum.controller

import com.forum.global.Constant
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
    @RequestMapping(value = "/limit")
     safeLimitRes(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute('errorMsg',Constant.LIMIT_MSG)
        return 'error.html'
    }
}

