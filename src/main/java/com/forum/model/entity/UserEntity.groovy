package com.forum.model.entity

class UserEntity {
    Integer id
    String sid
    String username
    String pws

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getSid() {
        return sid
    }

    void setSid(String sid) {
        this.sid = sid
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPws() {
        return pws
    }

    void setPws(String pws) {
        this.pws = pws
    }
}
