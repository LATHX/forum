package com.forum

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class Index {
    @RequestMapping('/index')
    index(){
        return '/index.html'
    }
    @RequestMapping('/red')
    red(){
        return '/index.html'
    }
}
