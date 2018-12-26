package com.forum.model.entity

class UserPostAndPostReplyEntity implements Serializable {
    private List<UserPostReplyVOEntity> userPostReplyVOEntity
    private UserPostVOEntity userPostVOEntity

    List<UserPostReplyVOEntity> getUserPostReplyVOEntity() {
        return userPostReplyVOEntity
    }

    void setUserPostReplyVOEntity(List<UserPostReplyVOEntity> userPostReplyVOEntity) {
        this.userPostReplyVOEntity = userPostReplyVOEntity
    }

    UserPostVOEntity getUserPostVOEntity() {
        return userPostVOEntity
    }

    void setUserPostVOEntity(UserPostVOEntity userPostVOEntity) {
        this.userPostVOEntity = userPostVOEntity
    }
}
