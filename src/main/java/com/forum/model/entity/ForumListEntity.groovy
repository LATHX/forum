package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_forumList')
class ForumListEntity implements Serializable{
    @Id
    String fid
    String fname
    String img
    String creator
    String description
    String creatorIP
    boolean enable
    boolean authority
    String date
    String type

    String getFid() {
        return fid
    }

    void setFid(String fid) {
        this.fid = fid
    }

    String getFname() {
        return fname
    }

    void setFname(String fname) {
        this.fname = fname
    }

    String getImg() {
        return img
    }

    void setImg(String img) {
        this.img = img
    }

    String getCreator() {
        return creator
    }

    void setCreator(String creator) {
        this.creator = creator
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getCreatorIP() {
        return creatorIP
    }

    void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP
    }

    boolean getEnable() {
        return enable
    }

    void setEnable(boolean enable) {
        this.enable = enable
    }

    boolean getAuthority() {
        return authority
    }

    void setAuthority(boolean authority) {
        this.authority = authority
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }
}
