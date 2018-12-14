package com.forum.service

import com.forum.model.dto.LoginInfo

import javax.servlet.http.HttpServletRequest

interface LoginService {
    String validationLoginInfo(HttpServletRequest request, LoginInfo loginInfo)
    String getToken(HttpServletRequest request)
    void logout()
}