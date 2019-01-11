package com.forum.model.entity

import org.apache.ibatis.annotations.Param

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_notification')
class NotificationEntity implements Serializable{
    String creator
    String creatorName
    String noun
    String updatetime
    String receiver
    String id

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getCreator() {
        return creator
    }

    void setCreator(@Param(value = 'creator')String creator) {
        this.creator = creator
    }

    String getCreatorName() {
        return creatorName
    }

    void setCreatorName(String creatorName) {
        this.creatorName = creatorName
    }

    String getNoun() {
        return noun
    }

    void setNoun(@Param(value = 'noun')String noun) {
        this.noun = noun
    }

    String getUpdatetime() {
        return updatetime
    }

    void setUpdatetime(@Param(value = 'updatetime')String updatetime) {
        this.updatetime = updatetime
    }

    String getReceiver() {
        return receiver
    }

    void setReceiver(@Param(value = 'receiver')String receiver) {
        this.receiver = receiver
    }
}
