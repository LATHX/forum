package com.forum.service

import com.forum.model.dto.MailInfo

interface MailService {
    void sendMail(MailInfo mailInfo)
}