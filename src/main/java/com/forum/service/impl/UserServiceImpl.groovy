package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.FollowFriendMapper
import com.forum.mapper.UserMapper
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.FollowFriendEntity
import com.forum.model.entity.UserEntity
import com.forum.model.entity.UserFollowCountVOEntity
import com.forum.rabbit.util.RabbitUtil
import com.forum.service.UserService
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper
    @Autowired
    FollowFriendMapper followFriendMapper

    @Override
    UserFollowCountVOEntity findUserBySid(String sid) {
        UserFollowCountVOEntity userFollowCountVOEntity = userMapper.findUserBySid(sid)
        if (CommonUtil.isEmpty(userFollowCountVOEntity.getFollowedCount())) {
            userFollowCountVOEntity.setFollowedCount("0")
        }
        if (CommonUtil.isEmpty(userFollowCountVOEntity.getFriendCount())) {
            userFollowCountVOEntity.setFriendCount("0")
        }
        if (CommonUtil.isEmpty(userFollowCountVOEntity.getForumCount())) {
            userFollowCountVOEntity.setForumCount("0")
        }
        return userFollowCountVOEntity
    }

    @Override
    MessageCodeInfo followFriendQueue(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (user == null || user.getSid() == followFriendEntity.getSid()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.LOGIN_OUT_MSG)
        } else {
            followFriendEntity.setSid(user.getSid())
            RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_USER_FOLLOW, followFriendEntity)
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            messageCodeInfo.setMsgInfo('')
        }
        return messageCodeInfo
    }

    @Override
    void followFriend(FollowFriendEntity followFriendEntity) {
        if (followFriendEntity.getSid() != followFriendEntity.getFriendSid()) {
            if (followFriendEntity.getOper() == '1') {
                Integer sidCount = userMapper.selectCountBySId(followFriendEntity.getFriendSid())
                Integer followCount = followFriendMapper.selectCountBySIdAndFriendId(followFriendEntity)
                if (sidCount == 1 && followCount == 0) {
                    followFriendMapper.insert(followFriendEntity)
                }
            } else if (followFriendEntity.getOper() == '-1') {
                followFriendMapper.deleteBySIdAndFriendId(followFriendEntity)
            }
        }
    }

    @Override
    MessageCodeInfo isFollowFriend(FollowFriendEntity followFriendEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        followFriendEntity.setSid(user.getSid())
        if (user != null && CommonUtil.isNotEmpty(followFriendEntity.getFriendSid()) && followFriendEntity.getSid() != followFriendEntity.getFriendSid()) {
            Integer followCount = followFriendMapper.selectCountBySIdAndFriendId(followFriendEntity)
            if (followCount == 1) {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            } else {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            }
            return messageCodeInfo
        }else if(followFriendEntity.getSid() == followFriendEntity.getFriendSid()){
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            return messageCodeInfo
        }
        messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        return messageCodeInfo
    }
}
