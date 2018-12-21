package com.forum.service

import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.UserForumListPostVOEntity

interface ForumService {
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority)
    List<UserForumListPostVOEntity> getSingleForumPostList(String fid)
}