package com.forum.service

import com.forum.model.dto.FavouriteInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowForumEntity
import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.UserForumListPostVOEntity
import com.forum.model.entity.UserPostAndPostReplyEntity

interface ForumService {
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority)
    List<UserForumListPostVOEntity> getSingleForumPostList(String fid, Integer page)
    List<UserForumListPostVOEntity> getRecommendPostList(Integer page)
    MessageCodeInfo favouriteQueue(FavouriteInfo favouriteInfo, MessageCodeInfo messageCodeInfo)
    void favouriteWriter(FavouriteInfo favouriteInfo)
    UserPostAndPostReplyEntity getSinglePost(String fid, String postId, String page, UserPostAndPostReplyEntity userPostAndPostReplyEntity )
    MessageCodeInfo followForum(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo)
}