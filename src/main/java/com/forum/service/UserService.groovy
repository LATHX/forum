package com.forum.service

import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowFriendEntity
import com.forum.model.entity.UserFollowCountVOEntity
import org.springframework.web.multipart.MultipartFile

interface UserService {
    UserFollowCountVOEntity findUserBySid(String sid)

    MessageCodeInfo followFriendQueue(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo)

    void followFriend(FollowFriendEntity followFriendEntity)

    MessageCodeInfo isFollowFriend(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo uploadPortrait(MultipartFile file, MessageCodeInfo messageCodeInfo)
}