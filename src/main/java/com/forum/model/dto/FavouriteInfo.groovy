package com.forum.model.dto

class FavouriteInfo implements Serializable{
    String operate
    String replyId
    String sid

    String getSid() {
        return sid
    }

    void setSid(String sid) {
        this.sid = sid
    }

    String getReplyId() {
        return replyId
    }

    void setReplyId(String replyId) {
        this.replyId = replyId
    }

    String getOperate() {
        return operate
    }

    void setOperate(String operate) {
        this.operate = operate
    }
}
