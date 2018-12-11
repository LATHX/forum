package com.forum.service

import com.forum.model.dto.MailInfo

interface MailService {
    void sendText(MailInfo mailInfo)
    void sendTextWithAttach(MailInfo mailInfo)
}