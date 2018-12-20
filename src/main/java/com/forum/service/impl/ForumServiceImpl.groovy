package com.forum.service.impl

import com.forum.global.Constant
import com.forum.mapper.ForumListMapper
import com.forum.model.entity.ForumListEntity
import com.forum.service.ForumService
import com.forum.utils.CommonUtil
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ForumServiceImpl implements ForumService{
    @Autowired
    ForumListMapper forumListMapper

    @Override
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority) {
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        List<ForumListEntity> list = null
        if(CommonUtil.isEmpty(type)){
            list = forumListMapper.selectAllFromTableByEnableAndAuthorityWithoutType(enable, authority)
        }else{
            list = forumListMapper.selectAllFromTableByEnableAndAuthorityWithType(enable, authority, type)
        }
        return list
    }
}
