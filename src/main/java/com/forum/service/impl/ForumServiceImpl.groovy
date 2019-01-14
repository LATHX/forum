package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.*
import com.forum.model.dto.FavouriteInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.*
import com.forum.rabbit.util.RabbitUtil
import com.forum.service.ForumService
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ForumServiceImpl implements ForumService {
    @Autowired
    ForumListMapper forumListMapper
    @Autowired
    UserForumListPostVOMapper userForumListPostVOMapper
    @Autowired
    UserPostReplyVOMapper userPostReplyVOMapper
    @Autowired
    ReplyFavouriteMapper replyFavouriteMapper
    @Autowired
    PostReplyMapper postReplyMapper
    @Autowired
    UserPostVOMapper userPostVOMapper
    @Autowired
    FollowForumMapper followForumMapper

    @Override
    List<ForumListEntity> getAllForumListByEnableAndAuthority(String type, Integer page, boolean enable, boolean authority) {
        page = page?.toString()?.isNumber() ? page : 1
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        List<ForumListEntity> list = null
        if (CommonUtil.isEmpty(type)) {
            list = forumListMapper.selectAllFromTableByEnableAndAuthorityWithoutType(enable, authority)
        } else {
            list = forumListMapper.selectAllFromTableByEnableAndAuthorityWithType(enable, authority, type)
        }
        return list
    }

    @Override
    List<UserForumListPostVOEntity> getSingleForumPostList(String fid, Integer page) {
        page = page?.toString()?.isNumber() ? page : 1
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        StringBuilder sb = new StringBuilder('')
        StringBuilder sb1 = new StringBuilder('')
        List<UserForumListPostVOEntity> list = userForumListPostVOMapper.userSingleForumPostList(fid)
        List<UserPostReplyVOEntity> bestList = null
        UserEntity userEntity = ShiroUtil.getUser()
        list?.eachWithIndex { current_postId, Idx ->
            sb.append('\"')
            sb.append(current_postId?.postid)
            sb.append('\"')
            if (Idx != list.size() - 1)
                sb.append(',')
        }
        if(list?.size() == 0){
            ForumListEntity forumList = forumListMapper.selectNameByFid(fid)
            UserForumListPostVOEntity userForumListPostVOEntity = new UserForumListPostVOEntity()
            userForumListPostVOEntity.setFname(forumList.getFname())
            list.add(userForumListPostVOEntity)
        }
        if (CommonUtil.isNotEmpty(sb.toString())) {
            bestList = userPostReplyVOMapper.SelectMaxFavouriteReplyByPostIdGroup(sb.toString())
            bestList?.eachWithIndex { current, Idx ->
                sb1.append('\"')
                sb1.append(current?.replyid)
                sb1.append('\"')
                if (Idx != bestList.size() - 1) {
                    sb1.append(',')
                }
                list?.find { it?.postid == current?.postid }?.setUserPostReplyVOEntity(current)
            }
        }
        if (userEntity != null && CommonUtil.isNotEmpty(sb1.toString())) {
            List<ReplyFavouriteEntity> favouriteList = replyFavouriteMapper.SelectFavouriteByReplyIdGroupAndSid(sb1.toString(), userEntity.getSid())
            favouriteList?.each { current ->
                bestList?.find { it?.replyid == current?.replyid }.setReplyFavouriteEntity(current)
            }
        }
        return list
    }

    @Override
    List<UserForumListPostVOEntity> getRecommendPostList(Integer page) {
        page = page?.toString()?.isNumber() ? page : 1
        PageHelper.startPage(page, Constant.PAGEROW.toInteger())
        StringBuilder sb = new StringBuilder('')
        StringBuilder sb1 = new StringBuilder('')
        List<UserForumListPostVOEntity> list = userForumListPostVOMapper.userRecommendPostList()
        List<UserPostReplyVOEntity> bestList = null
        UserEntity userEntity = ShiroUtil.getUser()
        list?.eachWithIndex { current_postId, Idx ->
            sb.append('\"')
            sb.append(current_postId?.postid)
            sb.append('\"')
            if (Idx != list.size() - 1)
                sb.append(',')
        }
        if (CommonUtil.isNotEmpty(sb.toString())) {
            bestList = userPostReplyVOMapper.SelectMaxFavouriteReplyByPostIdGroup(sb.toString())
            bestList?.eachWithIndex { current, Idx ->
                sb1.append('\"')
                sb1.append(current?.replyid)
                sb1.append('\"')
                if (Idx != bestList.size() - 1) {
                    sb1.append(',')
                }
                list?.find { it?.postid == current?.postid }?.setUserPostReplyVOEntity(current)
            }
        }
        if (userEntity != null && CommonUtil.isNotEmpty(sb1.toString())) {
            List<ReplyFavouriteEntity> favouriteList = replyFavouriteMapper.SelectFavouriteByReplyIdGroupAndSid(sb1.toString(), userEntity.getSid())
            favouriteList?.each { current ->
                bestList?.find { it?.replyid == current?.replyid }.setReplyFavouriteEntity(current)
            }
        }
        return list
    }

    @Override
    MessageCodeInfo favouriteQueue(FavouriteInfo favouriteInfo, MessageCodeInfo messageCodeInfo) {
        if (ShiroUtil.getUser() == null) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_OUT_MSG)
        } else if (CommonUtil.isEmpty(favouriteInfo.getOperate()) || CommonUtil.isEmpty(favouriteInfo.getReplyId()) || !(favouriteInfo.getOperate() in ['1', '0', '-1'])) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
        } else {
            favouriteInfo.setSid(ShiroUtil.getUser().getSid())
            RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_ADD_FAVOURITE, favouriteInfo)
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        }
        return messageCodeInfo
    }

    @Transactional
    @Override
    void favouriteWriter(FavouriteInfo favouriteInfo) {
        Integer count = replyFavouriteMapper.SelectFavouriteCountByReplyIdAndSid(favouriteInfo.getReplyId(), favouriteInfo.getSid())
        if (count != 0) {
            ReplyFavouriteEntity replyFavouriteEntity = replyFavouriteMapper.SelectFavouriteByReplyIdAndSid(favouriteInfo.getReplyId(), favouriteInfo.getSid())
            if (favouriteInfo?.getOperate() == '0') {
                replyFavouriteMapper.deleteFavourite(replyFavouriteEntity)
                PostReplyEntity postReplyEntity = postReplyMapper.SelectByReplyId(favouriteInfo.getReplyId())
                postReplyEntity.setFavourite(postReplyEntity.getFavourite() + (0 - (replyFavouriteEntity.getFavourite()?.toInteger())))
                postReplyMapper.updateByPrimaryKey(postReplyEntity)
            }
        } else {
            ReplyFavouriteEntity replyFavouriteEntity1 = new ReplyFavouriteEntity()
            replyFavouriteEntity1.setSid(favouriteInfo.getSid())
            replyFavouriteEntity1.setFavourite(favouriteInfo.getOperate()?.toInteger())
            replyFavouriteEntity1.setReplyid(favouriteInfo.getReplyId()?.toInteger())
            replyFavouriteMapper.insertToTable(replyFavouriteEntity1)
            PostReplyEntity postReplyEntity = postReplyMapper.SelectByReplyId(favouriteInfo.getReplyId())
            postReplyEntity.setFavourite(postReplyEntity.getFavourite() + (favouriteInfo.getOperate()?.toInteger()))
            postReplyMapper.updateByPrimaryKey(postReplyEntity)
        }
    }

    @Override
    UserPostAndPostReplyEntity getSinglePost(String fid, String postId, String page, UserPostAndPostReplyEntity userPostAndPostReplyEntity) {
        StringBuilder sb = new StringBuilder('')
        UserEntity userEntity = ShiroUtil.getUser()
        UserPostVOEntity userPostVOEntity = null
        if (page == '1') {
            userPostVOEntity = userPostVOMapper.SelectPostByPostId(postId)
        }
        PageHelper.startPage(page?.toInteger(), Constant.PAGEROW.toInteger())
        List<UserPostReplyVOEntity> replyList = userPostReplyVOMapper.SelectPostReplyByPostId(postId)
        replyList?.eachWithIndex { current, Idx ->
            sb.append('\"')
            sb.append(current?.replyid)
            sb.append('\"')
            if (Idx != replyList.size() - 1) {
                sb.append(',')
            }
        }
        if (userEntity != null && CommonUtil.isNotEmpty(sb.toString())) {
            List<ReplyFavouriteEntity> favouriteList = replyFavouriteMapper.SelectFavouriteByReplyIdGroupAndSid(sb.toString(), userEntity.getSid())
            favouriteList?.each { current ->
                replyList?.find { it?.replyid == current?.replyid }.setReplyFavouriteEntity(current)
            }
        }
        userPostAndPostReplyEntity.setUserPostReplyVOEntity(replyList)
        userPostAndPostReplyEntity.setUserPostVOEntity(userPostVOEntity)
        return userPostAndPostReplyEntity
    }

    @Override
    MessageCodeInfo followForumQueue(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (user == null) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_OUT_MSG)
        } else {
            followForumEntity.setSid(user.getSid())
            RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_USER_FOLLOW, followForumEntity)
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            messageCodeInfo.setMsgInfo('')
        }
        return messageCodeInfo
    }

    @Override
    void followForum(FollowForumEntity followForumEntity) {
        if (followForumEntity.getOper() == '1') {
            Integer fidCount = forumListMapper.selectCountByFId(followForumEntity.getFid())
            Integer followCount = followForumMapper.selectCountBySIdAndFId(followForumEntity)
            if (fidCount == 1 && followCount == 0) {
                followForumMapper.insert(followForumEntity)
            }
        } else if (followForumEntity.getOper() == '-1') {
            followForumMapper.deleteBySIdAndFId(followForumEntity)
        }
    }

    @Override
    MessageCodeInfo isFollowForum(FollowForumEntity followForumEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (user != null && CommonUtil.isNotEmpty(followForumEntity.getFid())) {
            followForumEntity.setSid(user.getSid())
            Integer followCount = followForumMapper.selectCountBySIdAndFId(followForumEntity)
            if (followCount == 1) {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            } else {
                followCount = forumListMapper.selectCountBySIdAndFId(user.getSid(), followForumEntity.getFid())
                if (followCount == 1) {
                    messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
                }else{
                    messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
                }
            }
            return messageCodeInfo
        }
        messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        return messageCodeInfo
    }

    @Override
    ForumListEntity findForumNameByFid(String fid, MessageCodeInfo messageCodeInfo) {
        ForumListEntity forumListEntity = forumListMapper.selectNameByFid(fid)
        return forumListEntity
    }

}
