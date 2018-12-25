package com.forum.service

import com.forum.model.dto.FavouriteInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.UserForumListPostVOEntity

interface ForumService {
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority)
    List<UserForumListPostVOEntity> getSingleForumPostList(String fid, Integer page)
    MessageCodeInfo favouriteQueue(FavouriteInfo favouriteInfo, MessageCodeInfo messageCodeInfo)
    void favouriteWriter(FavouriteInfo favouriteInfo)
}