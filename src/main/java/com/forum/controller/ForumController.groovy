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
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "论坛相关操作", tags = ['论坛相关操作'])
@RestController
@RequestMapping('/user-forum')
class ForumController {
    @Autowired
    ForumService forumService

    @ApiOperation('获取论坛列表')
    @ApiImplicitParams([
            @ApiImplicitParam(name = "type", value = "论坛类型", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String")
    ])
    @PostMapping('/forumlist')
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

    @ApiOperation('获取单个论坛内所有帖子')
    @ApiImplicitParams([
            @ApiImplicitParam(name = "fid", value = "论坛id", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String")
    ])
    @PostMapping('/single-forum-postlist')
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

    @ApiOperation('点赞帖子')
    @ApiImplicitParam(name = "favouriteInfo", value = "点赞信息", dataType = "FavouriteInfo")
    @PostMapping('/favourite')
    favourite(FavouriteInfo favouriteInfo, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        messageCodeInfo = forumService.favouriteQueue(favouriteInfo, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('获取单个帖子内详细内容')
    @ApiImplicitParams([
            @ApiImplicitParam(name = "fid", value = "论坛id", dataType = "String"),
            @ApiImplicitParam(name = "postid", value = "帖子id", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String")
    ])
    @PostMapping('/single-post')
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

    @ApiOperation('首页推荐帖子')
    @ApiImplicitParam(name = "page", value = "页码", dataType = "String")
    @PostMapping('/recommend-post')
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

    @ApiOperation('判断是否已关注论坛')
    @ApiImplicitParam(name = "followForumEntity", value = "关注论坛信息", dataType = "FollowForumEntity")
    @PostMapping('/isfollowforum')
    isFollowForum(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        messageCodeInfo = forumService.isFollowForum(followForumEntity, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('获取论坛名称')
    @ApiImplicitParam(name = "fid", value = "论坛Id", dataType = "String")
    @PostMapping('/forum-name')
    forumName(String fid, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if(CommonUtil.isEmpty(fid)){
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return forumService.findForumNameByFid(fid, messageCodeInfo)
    }
}
