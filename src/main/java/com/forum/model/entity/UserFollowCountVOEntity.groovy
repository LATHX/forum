package com.forum.model.entity

import javax.persistence.Entity

@Entity
class UserFollowCountVOEntity implements Serializable{
    private String sid
    private String username
    private String nickname
    private char sex
    private String province
    private String city
    private String dist
    private String userImg
    private String friendCount
    private String followedCount
    private String forumCount

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

    String getNickname() {
        return nickname
    }

    void setNickname(String nickname) {
        this.nickname = nickname
    }

    char getSex() {
        return sex
    }

    void setSex(char sex) {
        this.sex = sex
    }

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

    String getUserImg() {
        return userImg
    }

    void setUserImg(String userImg) {
        this.userImg = userImg
    }

    String getFriendCount() {
        return friendCount
    }

    void setFriendCount(String friendCount) {
        this.friendCount = friendCount
    }

    String getFollowedCount() {
        return followedCount
    }

    void setFollowedCount(String followedCount) {
        this.followedCount = followedCount
    }

    String getForumCount() {
        return forumCount
    }

    void setForumCount(String forumCount) {
        this.forumCount = forumCount
    }
}
