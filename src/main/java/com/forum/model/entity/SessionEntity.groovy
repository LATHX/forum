package com.forum.model.entity

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Component
@Entity
@Table(name = 'f_session')
@Scope(value = "prototype")
class SessionEntity implements Serializable {
    @Id
    Integer sessionid
    String username
    String cookie
    String updatetime
    String device

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

    String getDevice() {
        return device
    }

    void setDevice(String device) {
        this.device = device
    }
}
