package com.forum.controller

import com.forum.service.ForumService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping('/user-forum')
class ForumController {
    @Autowired
    ForumService forumService

    @RequestMapping('/forumlist')
    @ResponseBody
    getForumList(String type, Integer page){
        return forumService.getAllForumListByEnableAndAuthority(type, page, true, true)
    }

    @RequestMapping('/single-forum-postlist')
    @ResponseBody
    getSingleForumPostList(String fid,Integer page){
        return forumService.getSingleForumPostList(fid, page)
    }
}
