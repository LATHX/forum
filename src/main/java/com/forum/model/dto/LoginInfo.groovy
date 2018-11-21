package com.forum.model.dto

import org.springframework.stereotype.Component

@Component
class LoginInfo {
    String username
    String password
    String loginCode
    String publicKey
    MessageCodeInfo msg

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getLoginCode() {
        return loginCode
    }

    void setLoginCode(String loginCode) {
        this.loginCode = loginCode
    }

    String getPublicKey() {
        return publicKey
    }

    void setPublicKey(String publicKey) {
        this.publicKey = publicKey
    }

    MessageCodeInfo getMsg() {
        return msg
    }

    void setMsg(MessageCodeInfo msg) {
        this.msg = msg
    }
}
