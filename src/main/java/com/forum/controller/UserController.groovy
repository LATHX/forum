package com.forum.controller

import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowForumEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller('/user')
class UserController {
    @PostMapping('/follow-forum')
    @ResponseBody
    followForum(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo){

    }
}
