package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.FavouriteInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.service.ForumService
import com.forum.utils.CommonUtil
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
    getForumList(String type, String page, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (!CommonUtil.isNumber(page)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return forumService.getAllForumListByEnableAndAuthority(type, page?.toInteger(), true, true)
    }

    @RequestMapping('/single-forum-postlist')
    @ResponseBody
    getSingleForumPostList(String fid, String page, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (CommonUtil.isEmpty(fid) || !CommonUtil.isNumber(page)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return forumService.getSingleForumPostList(fid, page?.toInteger())
    }

    @RequestMapping('/favourite')
    @ResponseBody
    favourite(FavouriteInfo favouriteInfo, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo){
        messageCodeInfo = forumService.favouriteQueue(favouriteInfo, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }
}
