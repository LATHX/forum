package com.forum.service

import com.forum.model.dto.MessageCodeInfo
import com.forum.model.dto.RegisterInfo

import javax.servlet.http.HttpServletRequest

interface RegisterService {
    MessageCodeInfo register(HttpServletRequest request, RegisterInfo registerInfo, MessageCodeInfo messageCodeInfo)

    MessageCodeInfo registerMail(HttpServletRequest request, RegisterInfo registerInfo, MessageCodeInfo messageCodeInfo)
}