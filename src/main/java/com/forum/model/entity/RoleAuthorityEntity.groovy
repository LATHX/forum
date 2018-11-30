package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_role_authority')
class RoleAuthorityEntity implements Serializable {
    Integer roleId
    String authorityId

    Integer getRoleId() {
        return roleId
    }

    void setRoleId(Integer roleId) {
        this.roleId = roleId
    }

    String getAuthorityId() {
        return authorityId
    }

    void setAuthorityId(String authorityId) {
        this.authorityId = authorityId
    }
}
