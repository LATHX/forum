package com.forum.service

import com.forum.model.dto.LoginInfo
import com.forum.model.dto.MessageCodeInfo
import com.forum.model.entity.SessionEntity

import javax.servlet.http.HttpServletRequest

interface LoginService {
    MessageCodeInfo validationLoginInfo(HttpServletRequest request, LoginInfo loginInfo, MessageCodeInfo messageCodeInfo, SessionEntity sessionEntity)
    MessageCodeInfo getToken(HttpServletRequest request, LoginInfo loginInfo, MessageCodeInfo messageCodeInfo)
    void logout()
}