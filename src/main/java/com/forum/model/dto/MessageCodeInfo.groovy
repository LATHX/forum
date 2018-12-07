package com.forum.model.dto

import org.springframework.stereotype.Component

@Component
class MessageCodeInfo implements Serializable {
    String msgCode
    String msgInfo

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
