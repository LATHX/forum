package com.forum.controller

import com.forum.global.Constant
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class RedirectController {
    @RequestMapping('/index')
    main() {
        return 'index.html'
    }

    @RequestMapping('/index.html')
    index() {
        return 'index.html'
    }

    @RequestMapping('/loginpage')
    loginPage(HttpServletResponse response) throws Exception {
        response.sendRedirect(Constant.LOGIN_PAGE)
    }

    @RequestMapping('/forum_list')
    forumList() {
        return '/user/forum_list.html'
    }

    @RequestMapping('/index_main')
    indexMain() {
        return '/user/index_main.html'
    }

    @RequestMapping('/single_forum')
    singleForum(HttpServletRequest request, String fid) {
        request.setAttribute('fid', fid)
        return '/user/single_forum.html'
    }
}
