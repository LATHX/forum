package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_post_reply')
class PostReplyEntity {
    @Id
    Integer replyid
    Integer postid
    Integer favourite
    String creator
    char type
    String img0
    String img1
    String img2
    String img3
    String img4
    String img5
    String img6
    String img7
    String img8
    String video
    String date
    boolean enable
    String text
    String lastupdatetime

    Integer getFavourite() {
        return favourite
    }

    void setFavourite(Integer favourite) {
        this.favourite = favourite
    }

    Integer getReplyid() {
        return replyid
    }

    void setReplyid(Integer replyid) {
        this.replyid = replyid
    }

    Integer getPostid() {
        return postid
    }

    void setPostid(Integer postid) {
        this.postid = postid
    }

    String getCreator() {
        return creator
    }

    void setCreator(String creator) {
        this.creator = creator
    }

    char getType() {
        return type
    }

    void setType(char type) {
        this.type = type
    }

    String getImg0() {
        return img0
    }

    void setImg0(String img0) {
        this.img0 = img0
    }

    String getImg1() {
        return img1
    }

    void setImg1(String img1) {
        this.img1 = img1
    }

    String getImg2() {
        return img2
    }

    void setImg2(String img2) {
        this.img2 = img2
    }

    String getImg3() {
        return img3
    }

    void setImg3(String img3) {
        this.img3 = img3
    }

    String getImg4() {
        return img4
    }

    void setImg4(String img4) {
        this.img4 = img4
    }

    String getImg5() {
        return img5
    }

    void setImg5(String img5) {
        this.img5 = img5
    }

    String getImg6() {
        return img6
    }

    void setImg6(String img6) {
        this.img6 = img6
    }

    String getImg7() {
        return img7
    }

    void setImg7(String img7) {
        this.img7 = img7
    }

    String getImg8() {
        return img8
    }

    void setImg8(String img8) {
        this.img8 = img8
    }

    String getVideo() {
        return video
    }

    void setVideo(String video) {
        this.video = video
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }

    boolean getEnable() {
        return enable
    }

    void setEnable(boolean enable) {
        this.enable = enable
    }

    String getText() {
        return text
    }

    void setText(String text) {
        this.text = text
    }

    String getLastupdatetime() {
        return lastupdatetime
    }

    void setLastupdatetime(String lastupdatetime) {
        this.lastupdatetime = lastupdatetime
    }


}
