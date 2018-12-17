package com.forum.controller

import com.forum.global.Constant
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletResponse

@Controller
class RedirectController {
    @RequestMapping('/index')
    main() {
        return 'index.html'
    }

    @RequestMapping('/loginpage')
    loginPage(HttpServletResponse response) throws Exception {
        response.sendRedirect(Constant.LOGIN_PAGE)
    }

    @RequestMapping('/forumlist')
    forumList(){
        return '/forumlist.html'
    }
}
