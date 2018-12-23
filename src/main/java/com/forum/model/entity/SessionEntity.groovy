package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_session')
class SessionEntity {
    Integer sessionid
    String username
    String cookie
    String updatetime

    Integer getSessionid() {
        return sessionid
    }

    void setSessionid(Integer sessionid) {
        this.sessionid = sessionid
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getCookie() {
        return cookie
    }

    void setCookie(String cookie) {
        this.cookie = cookie
    }

    String getUpdatetime() {
        return updatetime
    }

    void setUpdatetime(String updatetime) {
        this.updatetime = updatetime
    }
}
