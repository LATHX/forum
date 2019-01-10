package com.forum.service

import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.*
import org.springframework.web.multipart.MultipartFile

interface UserService {
    UserFollowCountVOEntity findUserBySid(String sid)

    MessageCodeInfo followFriendQueue(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo)

    void followFriend(FollowFriendEntity followFriendEntity)

    MessageCodeInfo isFollowFriend(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo uploadPortrait(MultipartFile file, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo uploadBackgroundImage(MultipartFile file, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo editUserInfo(UserEntity userEntity, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo releasePost(MultipartFile[] file, String type, String title, String text, String[] remind, PostEntity postEntity, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo releasePostOnlyText(String type, String title, String text, String[] remind, PostEntity postEntity, MessageCodeInfo messageCodeInfo)

    List<UserEntity> FriendListBySId()

    void userNotification(Object obj)

}