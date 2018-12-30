package com.forum.service.impl

import com.forum.mapper.UserMapper
import com.forum.model.entity.UserEntity
import com.forum.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper
    @Override
    UserEntity findUserBySid(String sid) {
        return userMapper.findUserBySid(sid)
    }
}
