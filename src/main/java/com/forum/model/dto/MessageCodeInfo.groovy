package com.forum.model.dto

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(value = "prototype")
class MessageCodeInfo implements Serializable {
    private String msgCode
    private String msgInfo

    String getMsgCode() {
        return msgCode
    }

    void setMsgCode(String msgCode) {
        this.msgCode = msgCode
    }

    String getMsgInfo() {
        return msgInfo
    }

    void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo
    }
}
