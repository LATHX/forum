package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_non_authemticate')
class NonAuthemticateEntity {
    Integer id
    String url
    boolean enable

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    boolean getEnable() {
        return enable
    }

    void setEnable(boolean enable) {
        this.enable = enable
    }
}
