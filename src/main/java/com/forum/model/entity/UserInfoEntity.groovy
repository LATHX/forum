package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'userinfo_follow_count')
class UserInfoEntity implements Serializable{
    String sid;
    String friendCount
    String followedCount
    String forumCount

    String getSid() {
        return sid
    }

    void setSid(String sid) {
        this.sid = sid
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
