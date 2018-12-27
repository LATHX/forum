package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_follow_forum')
class FollowForumEntity implements Serializable{
    String sid
    String fid

    String getSid() {
        return sid
    }

    void setSid(String sid) {
        this.sid = sid
    }

    String getFid() {
        return fid
    }

    void setFid(String fid) {
        this.fid = fid
    }
}
