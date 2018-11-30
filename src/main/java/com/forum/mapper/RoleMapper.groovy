package com.forum.mapper

import com.forum.model.entity.RoleEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface RoleMapper extends Mapper<RoleEntity> {
    @Select('select * from f_role where role_id=#{roleId}')
    RoleEntity findRoleById(Integer roleId)

}