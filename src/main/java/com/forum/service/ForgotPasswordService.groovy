package com.forum.service

import com.forum.model.dto.MessageCodeInfo

import javax.servlet.http.HttpServletRequest

interface ForgotPasswordService {
    MessageCodeInfo sendForgotMail(HttpServletRequest request, String username)
    MessageCodeInfo restPassword()

}