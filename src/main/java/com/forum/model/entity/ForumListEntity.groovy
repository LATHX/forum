package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = 'f_forumList')
class ForumListEntity implements Serializable{
    String fid
    String fname
    String img
    String description
    String creator
    String creatorIP
    boolean enable
    boolean authority
    String type
    Timestamp date
    Timestamp getDate() {
        return date
    }

    void setDate(Timestamp date) {
        this.date = date
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

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

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getCreator() {
        return creator
    }

    void setCreator(String creator) {
        this.creator = creator
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
}
