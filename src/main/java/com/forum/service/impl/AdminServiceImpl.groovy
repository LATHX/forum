package com.forum.service.impl

import com.forum.mapper.ForumListMapper
import com.forum.model.entity.ForumListEntity
import com.forum.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl implements AdminService{
    @Autowired
    ForumListMapper forumListMapper
    @Override
    List<ForumListEntity> getForumList() {
        List<ForumListEntity> list = forumListMapper.selectAll()
        return list
    }
}
