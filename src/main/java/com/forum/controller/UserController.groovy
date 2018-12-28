package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowForumEntity
import com.forum.service.ForumService
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping('/user')
class UserController {
    @Autowired
    ForumService forumService

    @RequestMapping('/follow-forum')
    @ResponseBody
    followForum(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (CommonUtil.isEmpty(followForumEntity.getFid()) || CommonUtil.isEmpty(followForumEntity.getOper()) || !(followForumEntity.getOper() in ['1', '-1'])) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        messageCodeInfo = forumService.followForumQueue(followForumEntity, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }
}
