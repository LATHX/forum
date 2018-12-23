package com.forum.service

import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo

import javax.servlet.http.HttpServletRequest

interface LoginService {
    MessageCodeInfo validationLoginInfo(HttpServletRequest request, LoginInfo loginInfo)
    MessageCodeInfo getToken(HttpServletRequest request, LoginInfo loginInfo)
    void logout()
}