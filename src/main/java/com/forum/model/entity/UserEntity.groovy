package com.forum.model.entity

import org.crazycake.shiro.AuthCachePrincipal

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name='f_user')
class UserEntity implements Serializable, AuthCachePrincipal {
    private static final long serialVersionUID=1L;
    String sid
    String username
    String password
    String nickname
    Integer roleId
    boolean enable
    Integer sex
    String province
    String city
    String dist

    String getProvince() {
        return province
    }

    void setProvince(String province) {
        this.province = province
    }

    String getCity() {
        return city
    }

    void setCity(String city) {
        this.city = city
    }

    String getDist() {
        return dist
    }

    void setDist(String dist) {
        this.dist = dist
    }

    String getNickname() {
        return nickname
    }

    void setNickname(String nickname) {
        this.nickname = nickname
    }

    Integer getSex() {
        return sex
    }

    void setSex(Integer sex) {
        this.sex = sex
    }

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
