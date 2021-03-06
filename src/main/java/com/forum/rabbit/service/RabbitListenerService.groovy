package com.forum.rabbit.service

import com.forum.global.Constant
import com.forum.model.dto.FavouriteInfo
import com.forum.model.dto.MailInfo
import com.forum.model.entity.FollowForumEntity
import com.forum.model.entity.FollowFriendEntity
import com.forum.model.entity.NotificationEntity
import com.forum.model.entity.SessionEntity
import com.forum.redis.util.RedisUtil
import com.forum.service.ForumService
import com.forum.service.MailService
import com.forum.service.UserService
import com.forum.service.impl.ShiroServiceImpl
import com.forum.utils.CommonUtil
import com.forum.utils.ShiroUtil
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitListenerService {
    @Autowired
    MailService mailService
    @Autowired
    ShiroServiceImpl shiroService
    @Autowired
    ShiroUtil shiroUtil
    @Autowired
    ForumService forumService
    @Autowired
    UserService userService

    @RabbitListener(queues = 'secure.token_generate')
    void generateUUID() {
        System.out.println("UUID has Generate once")
        String redisQueueName = Constant.UUID_REDIS_QUEUE_NAME
        RedisUtil.lSet(redisQueueName, CommonUtil.generateUUID())
    }

    @RabbitListener(queues = 'secure.send_mail')
    void sendRegisterMail(byte[] msg) {
        println('Register Mail')
        MailInfo mailInfo = (MailInfo) CommonUtil.getObjectFromBytes(msg)
        println('Your code is:' + mailInfo.getText())
        mailService.sendMail(mailInfo)
    }

    @RabbitListener(queues = 'del.redis_key')
    void delRedisKey(byte[] msg) {
        println('Del Redis Key')
        String key = (String) CommonUtil.getObjectFromBytes(msg)
        RedisUtil.del(key)
    }

    @RabbitListener(queues = 'del.redis_user_session')
    void delRedisUserSession(byte[] msg) {
        println('Del Redis User Session')
        String username = (String) CommonUtil.getObjectFromBytes(msg)
        shiroUtil.kickOutUser(username, true)
    }

    @RabbitListener(queues = 'add.user_session')
    void addUserSession(byte[] msg) {
        println('Add User Session')
        SessionEntity sessionEntity = (SessionEntity) CommonUtil.getObjectFromBytes(msg)
        shiroService.addUserSession(sessionEntity)
    }

    @RabbitListener(queues = 'user.post_favourite')
    void addPostFavourite(byte[] msg) {
        FavouriteInfo favouriteInfo = (FavouriteInfo) CommonUtil.getObjectFromBytes(msg)
        forumService.favouriteWriter(favouriteInfo)
        userService.userNotification(favouriteInfo)
    }

    @RabbitListener(queues = 'user.follow')
    void userFollow(byte[] msg) {
        println('Follow Friend')
        Object obj = CommonUtil.getObjectFromBytes(msg)
        if (obj instanceof FollowForumEntity) {
            FollowForumEntity followForumEntity = (FollowForumEntity) obj
            forumService.followForum(followForumEntity)
        } else if (obj instanceof FollowFriendEntity) {
            FollowFriendEntity followFriendEntity = (FollowFriendEntity) obj
            userService.followFriend(followFriendEntity)
            userService.userNotification(obj)
        }else if(obj instanceof NotificationEntity){
            userService.userNotification(obj)
        }
    }

//    @RabbitListener(queues = 'user.follow')
//    void noticicationFollow(byte[] msg) {
//        Object obj = CommonUtil.getObjectFromBytes(msg)
//        userService.userNotification(obj)
//    }
//
//    @RabbitListener(queues = 'user.post_favourite')
//    void notificationPostFavourite(byte[] msg) {
//        println('Follow notification Friend')
//        Object obj = CommonUtil.getObjectFromBytes(msg)
//        userService.userNotification(obj)
//    }
}
