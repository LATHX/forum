package com.forum.service.impl

import com.forum.global.Constant
import com.forum.global.GlobalCode
import com.forum.mapper.FollowFriendMapper
import com.forum.mapper.NotificationMapper
import com.forum.mapper.PostMapper
import com.forum.mapper.UserMapper
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.*
import com.forum.rabbit.util.RabbitUtil
import com.forum.service.FileService
import com.forum.service.UserService
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper
    @Autowired
    PostMapper postMapper
    @Autowired
    NotificationMapper notificationMapper
    @Autowired
    FollowFriendMapper followFriendMapper
    @Autowired
    FileService fileService
    @Value('${web.upload-userimg-path}')
    private String userImgPath
    @Value('${web.upload-userbackgroundimg-path}')
    private String userBackgroundImgPath
    @Value('${web.upload-userpostimage-path}')
    private String userPostImgPath

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
        if (CommonUtil.isEmpty(userFollowCountVOEntity.getUserImg())) {
            userFollowCountVOEntity.setUserImg('')
        }
        if (CommonUtil.isEmpty(userFollowCountVOEntity.getUserBackgroundImg())) {
            userFollowCountVOEntity.setUserBackgroundImg('')
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
        followFriendEntity.setSid(user?.getSid())
        if (user != null && CommonUtil.isNotEmpty(followFriendEntity.getFriendSid()) && followFriendEntity.getSid() != followFriendEntity.getFriendSid()) {
            Integer followCount = followFriendMapper.selectCountBySIdAndFriendId(followFriendEntity)
            if (followCount == 1) {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            } else {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            }
            return messageCodeInfo
        } else if (followFriendEntity.getSid() == followFriendEntity.getFriendSid()) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            return messageCodeInfo
        }
        messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        return messageCodeInfo
    }

    @Override
    MessageCodeInfo uploadPortrait(MultipartFile file, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (user == null) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            return messageCodeInfo
        }
        String diskFileName = fileService.save(userImgPath, file)
        UserEntity userEntity = userMapper.selectByPrimaryKey(user.getSid())
        userEntity.setUserImg("/images/userImg/" + diskFileName)
        Integer updateRow = userMapper.updateByPrimaryKey(userEntity)
        if (updateRow == 1) {
            user.setUserImg(userEntity.getUserImg())
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        } else {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        return messageCodeInfo

    }

    @Override
    MessageCodeInfo uploadBackgroundImage(MultipartFile file, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (user == null) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            return messageCodeInfo
        }
        String diskFileName = fileService.save(userBackgroundImgPath, file)
        UserEntity userEntity = userMapper.selectByPrimaryKey(user.getSid())
        userEntity.setUserBackgroundImg("/images/userBackgroundImg/" + diskFileName)
        Integer updateRow = userMapper.updateByPrimaryKey(userEntity)
        if (updateRow == 1) {
            user.setUserBackgroundImg(userEntity.getUserImg())
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        } else {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        return messageCodeInfo

    }

    @Override
    MessageCodeInfo editUserInfo(UserEntity userEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (!userEntity.getSex() in ['0', '1']) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
        } else {
            UserEntity userEntity1 = userMapper.selectByPrimaryKey(user.getSid())
            userEntity1.setSex(userEntity.getSex())
            Integer updateRow = userMapper.updateByPrimaryKey(userEntity1)
            if (updateRow == 1) {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            } else {
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
                messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            }
        }
        return messageCodeInfo
    }

    @Override
    MessageCodeInfo releasePost(MultipartFile[] file, String type, String title, String text, String[] remind, PostEntity postEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if (user == null) {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            return messageCodeInfo
        }
        if (type == '1') {
            file?.eachWithIndex { current_file, idx ->
                String diskFileName = fileService.save(userPostImgPath, current_file)
                postEntity.setImg(idx, '/images/userPostImg/' + diskFileName)
            }
        } else if (type == '2') {
            file?.eachWithIndex { current_file, idx ->
                String diskFileName = fileService.save(userPostImgPath, current_file)
                postEntity.setVideo('/images/userPostImg/' + diskFileName)
            }
        }
        postEntity.setText(CommonUtil.filterXSS(text))
        postEntity.setTitle(CommonUtil.filterXSS(title))
        postEntity.setType(type)
        postEntity.setCreator(user.getSid())
        Integer updateRow = postMapper.insertSelective(postEntity)
        StringBuilder sb = new StringBuilder('')
        if (updateRow == 1) {
            remind?.each {
                NotificationEntity notificationEntity = new NotificationEntity()
                notificationEntity.setCreator(user.getSid())
                notificationEntity.setCreatorName(user.getUsername())
                notificationEntity.setReceiver(it)
                sb.append(String.format("<a href='javascript:void(0)' data-sid='%s' data-oper='1' data-toggle='modal' data-target='#friend_modal'>", user.getSid()))
                sb.append(user.getUsername()).append("</a>&nbsp;在帖子&nbsp;")
                sb.append(String.format('<a href=\'/single_post?postid=%s&fid=%s\'>', postEntity.getPostid(), postEntity.getFid()))
                sb.append(title)
                sb.append('</a>&nbsp;提到了你')
                notificationEntity.setNoun(sb.toString())
                RabbitUtil.deliveryMessageNotConfirm(Constant.MQ_USER_FOLLOW, notificationEntity)
            }
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        } else {
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        return messageCodeInfo
    }

    @Override
    MessageCodeInfo releasePostOnlyText(String type, String title, String text, String[] remind, PostEntity postEntity, MessageCodeInfo messageCodeInfo) {
        return releasePost(null, type, title, text, remind, postEntity, messageCodeInfo)
    }

    @Override
    List<UserEntity> FriendListBySId() {
        UserEntity user = ShiroUtil.getUser()
        List<UserEntity> friendList = followFriendMapper.selectFriendListBySId(user.getSid())
        return friendList
    }

    @Override
    void userNotification(Object obj) {
        if (obj instanceof NotificationEntity) {
            notificationMapper.insertSelective(obj)
        } else if (obj instanceof FollowFriendEntity) {
            NotificationEntity notificationEntity = new NotificationEntity()
            FollowFriendEntity followFriendEntity1 = (FollowFriendEntity) obj
            UserEntity userEntity1 = userMapper.selectNicknameBySId(followFriendEntity1?.getSid())
            if (CommonUtil.isNotEmpty(userEntity1?.getUsername())) {
                notificationEntity.setCreator(followFriendEntity1?.getSid())
                notificationEntity.setCreatorName(userEntity1?.getUsername())
                notificationEntity.setReceiver(followFriendEntity1?.friendSid)
                StringBuilder sb = new StringBuilder('')
                sb.append(String.format("<a href='javascript:void(0)' data-sid='%s' data-oper='1' data-toggle='modal' data-target='#friend_modal'>", followFriendEntity1.getSid()))
                sb.append(userEntity1.getUsername()).append("</a>&nbsp;关注了你&nbsp;")
                notificationEntity.setNoun(sb.toString())
                notificationMapper.insertSelective(notificationEntity)
            }
        }
    }
}
