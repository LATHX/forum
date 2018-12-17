package com.forum.model.entity

import org.apache.ibatis.annotations.Param
import org.crazycake.shiro.AuthCachePrincipal

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_user')
class UserEntity implements Serializable, AuthCachePrincipal {
    private static final long serialVersionUID = 1L;
    @Id
    private String sid
    private String username
    private String password
    private String nickname
    private Integer roleId
    private boolean enable
    private char sex
    private String province
    private String city
    private String dist
    private String userImg

    String getUserImg() {
        return userImg
    }

    void setUserImg(String userImg) {
        this.userImg = userImg
    }

    char getSex() {
        return sex
    }

    void setSex(@Param(value="sex") char sex) {
        this.sex = sex
    }

    String getProvince() {
        return province
    }

    void setProvince(@Param(value="province")String province) {
        this.province = province
    }

    String getCity() {
        return city
    }

    void setCity(@Param(value="city")String city) {
        this.city = city
    }

    String getDist() {
        return dist
    }

    void setDist(@Param(value="dist")String dist) {
        this.dist = dist
    }

    String getNickname() {
        return nickname
    }

    void setNickname(@Param(value="nickname")String nickname) {
        this.nickname = nickname
    }


    boolean getEnable() {
        return enable
    }

    void setEnable(@Param(value="enable")boolean enable) {
        this.enable = enable
    }

    String getSid() {
        return sid
    }

    void setSid(@Param(value="sid")String sid) {
        this.sid = sid
    }

    String getUsername() {
        return username
    }

    void setUsername(@Param(value="username")String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(@Param(value="password")String password) {
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
