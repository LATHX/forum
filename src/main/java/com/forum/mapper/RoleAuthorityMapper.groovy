package com.forum.mapper

import com.forum.model.entity.AuthorityEntity
import com.forum.model.entity.RoleAuthorityEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface RoleAuthorityMapper extends Mapper<RoleAuthorityEntity> {
    @Select('select * from f_role_authority t1 INNER JOIN f_authority t2 on t1.authority_id = t2.authority_id where t1.role_id=#{roleId}')
    List<AuthorityEntity> findAuthoritiesByRoleId(Integer roleId)


}