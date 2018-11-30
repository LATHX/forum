package com.forum.mapper

import com.forum.model.entity.AuthorityEntity
import com.forum.model.entity.RoleAuthorityEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

import javax.persistence.Entity
import javax.persistence.Table

@org.apache.ibatis.annotations.Mapper
interface RoleAuthorityMapper extends Mapper<RoleAuthorityEntity> {
    @Select('select * from f_role_authority where role_id=#{roleId}')
    List<AuthorityEntity> findAuthoritiesByRoleId(Integer roleId)


}