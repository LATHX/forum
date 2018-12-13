package com.forum.model.dto

import com.forum.model.validationInterface.LoginGroup
import org.springframework.stereotype.Component

import javax.validation.constraints.NotBlank

@Component
class LoginInfo implements Serializable {
    @NotBlank(message = '{login.username.blank}', groups = [LoginGroup.class])
    private String username
    @NotBlank(message = '{login.password.blank}', groups = [LoginGroup.class])
    private String password
    @NotBlank(message = '{login.token.blank}', groups = [LoginGroup.class])
    private String token
    @NotBlank(message = '{login.publicKey.blank}')
    private String publicKey
    private String rememberMe;
    private MessageCodeInfo msg

    String getRememberMe() {
        return rememberMe
    }

    void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe
    }

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
