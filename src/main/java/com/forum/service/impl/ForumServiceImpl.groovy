package com.forum.service.impl

import com.forum.global.Constant
import com.forum.mapper.ForumListMapper
import com.forum.mapper.UserForumListPostVOMapper
import com.forum.mapper.UserPostReplyVOMapper
import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.UserForumListPostVOEntity
import com.forum.model.entity.UserPostReplyVOEntity
import com.forum.service.ForumService
import com.forum.utils.CommonUtil
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ForumServiceImpl implements ForumService{
    @Autowired
    ForumListMapper forumListMapper
    @Autowired
    UserForumListPostVOMapper userForumListPostVOMapper
    @Autowired
    UserPostReplyVOMapper userPostReplyVOMapper

    @Override
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority) {
        page = page?.toString()?.isNumber()? page : 1
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        List<ForumListEntity> list = null
        if(CommonUtil.isEmpty(type)){
            list = forumListMapper.selectAllFromTableByEnableAndAuthorityWithoutType(enable, authority)
        }else{
            list = forumListMapper.selectAllFromTableByEnableAndAuthorityWithType(enable, authority, type)
        }
        return list
    }

    @Override
    List<UserForumListPostVOEntity> getSingleForumPostList(String fid, Integer page) {
        page = page?.toString()?.isNumber()? page : 1
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        StringBuilder sb = new StringBuilder('')
        List<UserForumListPostVOEntity> list = userForumListPostVOMapper.userSingleForumPostList(fid)
        list?.eachWithIndex { current_postId, Idx ->
            sb.append('\"')
            sb.append(current_postId?.postid)
            sb.append('\"')
            if(Idx != list.size() - 1)
            sb.append(',')
        }
        List<UserPostReplyVOEntity> favouriteList = userPostReplyVOMapper.SelectMaxFavouriteReplyByPostIdGroup(sb.toString())
        favouriteList?.each {current ->
            list?.find{it?.postid == current?.postid}?.setUserPostReplyVOEntity(current)
        }
        return list
    }
}
