package com.forum.service.impl

import com.forum.global.Constant
import com.forum.mapper.ForumListMapper
import com.forum.model.entity.ForumListEntity
import com.forum.service.ForumService
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ForumServiceImpl implements ForumService{
    @Autowired
    ForumListMapper forumListMapper

    @Override
    List<ForumListEntity> getAllForumListByEnableAndAuthority(Integer page, boolean enable, boolean authority) {
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        List<ForumListEntity> list = forumListMapper.selectAllFromTableByEnableAndAuthority(enable, authority)
        return list
    }
}
