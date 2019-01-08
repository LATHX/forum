package com.forum.controller

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.model.dto.CommonInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowForumEntity
import com.forum.model.entity.FollowFriendEntity
import com.forum.model.entity.UserEntity
import com.forum.service.ForumService
import com.forum.service.UserService
import com.forum.utils.CommonUtil
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Api(value = "用户信息", tags = ['用户信息相关操作'])
@RestController
@RequestMapping('/user')
class UserController {
    @Autowired
    ForumService forumService
    @Autowired
    UserService userService

    @ApiOperation('用户关注论坛')
    @ApiImplicitParam(name = "followForumEntity", value = "用户关注论坛信息", dataType = "FollowForumEntity")
    @RequestMapping('/follow-forum')
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

    @ApiOperation('用户关注好友')
    @ApiImplicitParam(name = "followFriendEntity", value = "用户关注好友信息", dataType = "FollowFriendEntity")
    @RequestMapping('/follow-friend')
    followFriend(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (CommonUtil.isEmpty(followFriendEntity.getFriendSid()) || CommonUtil.isEmpty(followFriendEntity.getOper()) || !(followFriendEntity.getOper() in ['1', '-1'])) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        messageCodeInfo = userService.followFriendQueue(followFriendEntity, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('获取用户信息')
    @ApiImplicitParam(name = "sid", value = "用户id", dataType = "String")
    @RequestMapping('/user-info')
    findUserInfo(String sid, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (CommonUtil.isEmpty(sid)) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            commonInfo.setMsg(messageCodeInfo)
            return commonInfo
        }
        return userService.findUserBySid(sid)
    }

    @ApiOperation('是否已关注好友')
    @ApiImplicitParam(name = "followFriendEntity", value = "关注好友信息", dataType = "FollowFriendEntity")
    @RequestMapping('/isfollowfriend')
    isFollowForum(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        messageCodeInfo = userService.isFollowFriend(followFriendEntity, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('用户修改个人头像')
    @RequestMapping('/edit-portrait')
    uploadPortrait(
            @RequestParam("uploadUserinfo") MultipartFile file, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (file.isEmpty()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        messageCodeInfo = userService.uploadPortrait(file, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('用户修改个人背景')
    @RequestMapping('/edit-background')
    uploadBackground(
            @RequestParam("uploadUserinfoBackgroung") MultipartFile file, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        if (file.isEmpty()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        messageCodeInfo = userService.uploadBackgroundImage(file, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }

    @ApiOperation('用户修改个人信息')
    @ApiImplicitParam(name = "userEntity", value = "用户信息", dataType = "UserEntity")
    @RequestMapping('/edit-info')
    editUserInfo(
            UserEntity userEntity, MessageCodeInfo messageCodeInfo, CommonInfo commonInfo) {
        messageCodeInfo = userService.editUserInfo(userEntity, messageCodeInfo)
        commonInfo.setMsg(messageCodeInfo)
        return commonInfo
    }
}
