package com.forum.service

import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo

import javax.servlet.http.HttpServletRequest

interface ForgotPasswordService {
    MessageCodeInfo sendForgotMail(HttpServletRequest request, String username, MessageCodeInfo messageCodeInfo)
    MessageCodeInfo restPassword(HttpServletRequest request, RegisterInfo registerInfo, MessageCodeInfo messageCodeInfo)

}