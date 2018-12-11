package com.forum.service

import com.forum.model.dto.RegisterInfo

import javax.servlet.http.HttpServletRequest

interface RegisterService {
     boolean register(HttpServletRequest request, RegisterInfo registerInfo)
    boolean registerMail(HttpServletRequest request, RegisterInfo registerInfo)
}