package com.forum.model.dto

import org.springframework.stereotype.Component

@Component
class LoginInfo {
    String username
    String password
    String token
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

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
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
