package com.forum.model.entity
class User {
    String sid
    String username
    String psw

    String getSid() {
        return sid
    }

    void setSid(String sid) {
        this.sid = sid
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPsw() {
        return psw
    }

    void setPsw(String psw) {
        this.psw = psw
    }
}