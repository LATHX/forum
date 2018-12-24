package com.forum.utils

import com.forum.mapper.SessionMapper
import com.forum.model.entity.SessionEntity
import com.forum.model.entity.UserEntity
import com.forum.redis.util.RedisUtil
import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.Session
import org.apache.shiro.subject.SimplePrincipalCollection
import org.apache.shiro.subject.support.DefaultSubjectContext
import org.crazycake.shiro.RedisSessionDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ShiroUtil {
    private static String SESSION_KEY = 'shiro:session:'
    @Autowired
    SessionMapper sessionMapper
/**
     * 获取指定用户名的Session
     * @param username
     * @return
     */
    private static Session getSessionByUsername(String username) {
        RedisSessionDAO redisSessionDAO = SpringUtil.getBean(RedisSessionDAO.class)
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        UserEntity user;
        Object attribute;
        for (Session session : sessions) {
            attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            user = (UserEntity) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (user == null) {
                continue;
            }
            if (Objects.equals(user.getUsername(), username)) {
                return session;
            }
        }
        return null;
    }

    /**
     * 删除用户缓存信息
     * @param username 用户名
     * @param isRemoveSession 是否删除session，删除后用户需重新登录
     */
     void kickOutUser(String username, boolean isRemoveSession) throws Exception {
        List<SessionEntity> list = sessionMapper.SelectAllCookieByUsername(username)
        list.each {
            String key = SESSION_KEY + it.getCookie()
            RedisUtil.del(key)
        }
        sessionMapper.DeleteAllByUsername(username)
    }

    static UserEntity getUser() {
        Object attribute = SecurityUtils.getSubject()?.getSession()?.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)
        UserEntity user = (UserEntity) ((SimplePrincipalCollection) attribute)?.getPrimaryPrincipal()
        if (user == null) return null
        user.setPassword(null)
        return user
    }
}
