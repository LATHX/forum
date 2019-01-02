package com.forum.service

import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowFriendEntity
import com.forum.model.entity.UserFollowCountVOEntity

interface UserService {
    UserFollowCountVOEntity findUserBySid(String sid)

    MessageCodeInfo followFriendQueue(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo)

    void followFriend(FollowFriendEntity followFriendEntity)

    MessageCodeInfo isFollowFriend(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo)
}