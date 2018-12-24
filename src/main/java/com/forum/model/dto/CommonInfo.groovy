package com.forum.model.dto

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(value = "prototype")
class CommonInfo {
    private MessageCodeInfo msg

    MessageCodeInfo getMsg() {
        return msg
    }

    void setMsg(MessageCodeInfo msg) {
        this.msg = msg
    }
}
