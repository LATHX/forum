package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_non_authemticate')
class NonAuthemticateEntity implements Serializable {
    @Id
    private Integer id
    private String url
    private String description
    private boolean enable

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

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
