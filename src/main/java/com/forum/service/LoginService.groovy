package com.forum.service

import com.forum.model.dto.LoginInfo

interface LoginService {
    String validationLoginInfo(String ip, LoginInfo loginInfo)
    String getToken(String ip)
    void logout()
}