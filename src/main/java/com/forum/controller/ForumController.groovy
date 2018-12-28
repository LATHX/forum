package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.FavouriteInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowForumEntity
import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.UserForumListPostVOEntity
import com.forum.model.entity.UserPostAndPostReplyEntity
import com.forum.service.ForumService
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping('/user-forum')
class ForumController {
    @Autowired
    ForumService forumService

    @PostMapping('/forumlist')
    @ResponseBody
    getForumList(String type, String page, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (!CommonUtil.isNumber(page)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        List<ForumListEntity> list = forumService.getAllForumListByEnableAndAuthority(type, page?.toInteger(), true, true)
        if (list?.size() == 0) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_DATA_COMPLETE)
            messageCodeInfo.setMsgInfo(Constant.DATA_COMPLETE_MSG)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return list
    }

    @PostMapping('/single-forum-postlist')
    @ResponseBody
    getSingleForumPostList(String fid, String page, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (CommonUtil.isEmpty(fid) || !CommonUtil.isNumber(page)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        List<UserForumListPostVOEntity> list = forumService.getSingleForumPostList(fid, page?.toInteger())
        if (list?.size() == 0) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_DATA_COMPLETE)
            messageCodeInfo.setMsgInfo(Constant.DATA_COMPLETE_MSG)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return list
    }

    @PostMapping('/favourite')
    @ResponseBody
    favourite(FavouriteInfo favouriteInfo, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        messageCodeInfo = forumService.favouriteQueue(favouriteInfo, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @RequestMapping('/single-post')
    @ResponseBody
    getSinglePost(String fid, String postid, String page, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo, UserPostAndPostReplyEntity userPostAndPostReplyEntity) {
        if (!CommonUtil.isNumber(page) || CommonUtil.isEmpty(fid) || CommonUtil.isEmpty(postid)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        userPostAndPostReplyEntity = forumService.getSinglePost(fid, postid, page, userPostAndPostReplyEntity)
        if (userPostAndPostReplyEntity.getUserPostReplyVOEntity()?.size() == 0 && page.toInteger() > 1) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_DATA_COMPLETE)
            messageCodeInfo.setMsgInfo(Constant.DATA_COMPLETE_MSG)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return userPostAndPostReplyEntity
    }

    @RequestMapping('/recommend-post')
    @ResponseBody
    recommandPost(String page, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (!CommonUtil.isNumber(page)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        List<UserForumListPostVOEntity> list = forumService.getRecommendPostList(page?.toInteger())
        if (list?.size() == 0) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_DATA_COMPLETE)
            messageCodeInfo.setMsgInfo(Constant.DATA_COMPLETE_MSG)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return list
    }

    @RequestMapping('/isfollowforum')
    @ResponseBody
    isFollowForum(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        messageCodeInfo = forumService.isFollowForum(followForumEntity, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }
}
