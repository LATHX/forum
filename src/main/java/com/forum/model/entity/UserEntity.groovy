package com.forum.model.entity

import org.crazycake.shiro.AuthCachePrincipal

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name='f_user')
class UserEntity implements Serializable, AuthCachePrincipal {
    String sid
    String username
    String password
    Integer roleId
    boolean enable

    boolean getEnable() {
        return enable
    }

    void setEnable(boolean enable) {
        this.enable = enable
    }

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

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    Integer getRoleId() {
        return roleId
    }

    void setRoleId(Integer roleId) {
        this.roleId = roleId
    }
    @Override
    public String getAuthCacheKey() {
        return username;
    }
}
