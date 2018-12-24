package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_authority')
class AuthorityEntity implements Serializable {
    @Id
    private Integer authorityId
    private String authorityName
    private String icon
    private String uri
    private String permission

    Integer getAuthorityId() {
        return authorityId
    }

    void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId
    }

    String getAuthorityName() {
        return authorityName
    }

    void setAuthorityName(String authorityName) {
        this.authorityName = authorityName
    }

    String getIcon() {
        return icon
    }

    void setIcon(String icon) {
        this.icon = icon
    }

    String getUri() {
        return uri
    }

    void setUri(String uri) {
        this.uri = uri
    }

    String getPermission() {
        return permission
    }

    void setPermission(String permission) {
        this.permission = permission
    }
}
