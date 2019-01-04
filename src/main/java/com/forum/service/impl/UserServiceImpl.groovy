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
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper
    @Autowired
    FollowFriendMapper followFriendMapper
    @Value('${web.upload-userimg-path}')
    private String userImgPath
    @Value('${web.upload-userbackgroundimg-path}')
    private String userBackgroundImgPath

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
        if(CommonUtil.isEmpty(userFollowCountVOEntity.getUserImg())){
            userFollowCountVOEntity.setUserImg('')
        }
        if(CommonUtil.isEmpty(userFollowCountVOEntity.getUserBackgroundImg())){
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
        }else if(followFriendEntity.getSid() == followFriendEntity.getFriendSid()){
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            return messageCodeInfo
        }
        messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        return messageCodeInfo
    }

    @Override
    MessageCodeInfo uploadPortrait(MultipartFile file, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if(user == null){
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            return messageCodeInfo
        }
        String name = ""
        String pathname = ""
        String fileName = file.getOriginalFilename()
//        File path = new File(ResourceUtils.getURL("classpath:").getPath());
//        File uploadFile = new File(path.getAbsolutePath(), "static/images/userImg/");//开发测试模式中 获取到的是/target/classes/static/images/upload/
//        if (!uploadFile.exists()){
//            uploadFile.mkdirs();
//        }
        File uploadFile = new File(userImgPath);
        if (!uploadFile.exists()){
            uploadFile.mkdirs();
        }
        //获取文件后缀名
        String end = CommonUtil.getExtension(file.getOriginalFilename());
        name = CommonUtil.generateUUID() + user.getSid().substring(0, 5)
        String diskFileName = name + "." +end; //目标文件的文件名
        pathname = userImgPath + diskFileName;
        UserEntity userEntity = userMapper.selectByPrimaryKey(user.getSid())
        userEntity.setUserImg("/images/userImg/" + diskFileName)
        Integer updateRow = userMapper.updateByPrimaryKey(userEntity)
        if(updateRow == 1){
            file.transferTo(new File(pathname));//文件转存
            user.setUserImg(userEntity.getUserImg())
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        }else{
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        return messageCodeInfo

    }
    @Override
    MessageCodeInfo uploadBackgroundImage(MultipartFile file, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if(user == null){
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            return messageCodeInfo
        }
        String name = ""
        String pathname = ""
        String fileName = file.getOriginalFilename()
        File uploadFile = new File(userBackgroundImgPath);
        if (!uploadFile.exists()){
            uploadFile.mkdirs();
        }
        //获取文件后缀名
        String end = CommonUtil.getExtension(file.getOriginalFilename());
        name = CommonUtil.generateUUID() + user.getSid().substring(0, 5)
        String diskFileName = name + "." +end; //目标文件的文件名
        pathname = userBackgroundImgPath + diskFileName;
        UserEntity userEntity = userMapper.selectByPrimaryKey(user.getSid())
        userEntity.setUserBackgroundImg("/images/userBackgroundImg/" + diskFileName)
        Integer updateRow = userMapper.updateByPrimaryKey(userEntity)
        if(updateRow == 1){
            file.transferTo(new File(pathname));//文件转存
            user.setUserBackgroundImg(userEntity.getUserImg())
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
        }else{
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
        }
        return messageCodeInfo

    }

    @Override
    MessageCodeInfo editUserInfo(UserEntity userEntity, MessageCodeInfo messageCodeInfo) {
        UserEntity user = ShiroUtil.getUser()
        if(!userEntity.getSex() in ['0','1']){
            messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
            messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
        }else{
            UserEntity userEntity1 = userMapper.selectByPrimaryKey(user.getSid())
            userEntity1.setSex(userEntity.getSex())
            Integer updateRow = userMapper.updateByPrimaryKey(userEntity1)
            if(updateRow == 1){
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_SUCCESS)
            }else{
                messageCodeInfo.setMsgCode(GlobalCode.REFERENCE_FAIL)
                messageCodeInfo.setMsgInfo(Constant.ERROR_PARAM)
            }
        }
        return messageCodeInfo
    }
}
