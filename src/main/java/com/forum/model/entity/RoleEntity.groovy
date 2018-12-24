package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_role')
class RoleEntity implements Serializable {
    @Id
    private Integer roleId
    private String roleName

    Integer getRoleId() {
        return roleId
    }

    void setRoleId(Integer roleId) {
        this.roleId = roleId
    }

    String getRoleName() {
        return roleName
    }

    void setRoleName(String roleName) {
        this.roleName = roleName
    }
}
