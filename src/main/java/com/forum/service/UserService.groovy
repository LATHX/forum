package com.forum.service

import com.forum.model.entity.UserEntity

interface UserService {
    UserEntity findUserBySid(String sid)
}