package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_reply_favourite')
class ReplyFavouriteEntity implements Serializable {
    Integer replyid
    String sid
    Integer favourite

    Integer getReplyid() {
        return replyid
    }

    void setReplyid(Integer replyid) {
        this.replyid = replyid
    }

    String getSid() {
        return sid
    }

    void setSid(String sid) {
        this.sid = sid
    }

    Integer getFavourite() {
        return favourite
    }

    void setFavourite(Integer favourite) {
        this.favourite = favourite
    }
}
