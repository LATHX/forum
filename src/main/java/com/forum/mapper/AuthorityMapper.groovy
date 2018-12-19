package com.forum.mapper

import com.forum.model.entity.AuthorityEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface AuthorityMapper extends Mapper<AuthorityEntity> {
    @Select('select * from f_authority')
    List<AuthorityEntity> selectAllFromTable()

}