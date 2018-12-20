package com.forum.service

import com.forum.model.entity.ForumListEntity

interface ForumService {
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority)
}