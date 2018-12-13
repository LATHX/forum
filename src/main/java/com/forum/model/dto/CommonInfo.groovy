package com.forum.model.dto

import org.springframework.stereotype.Component

@Component
class CommonInfo {
    private MessageCodeInfo msg

    MessageCodeInfo getMsg() {
        return msg
    }

    void setMsg(MessageCodeInfo msg) {
        this.msg = msg
    }
}
