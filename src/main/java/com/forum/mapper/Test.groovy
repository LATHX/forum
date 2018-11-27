package com.forum.mapper

import com.forum.model.entity.UserEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface Test {
    @Select('select * from f_user')
    UserEntity findPsw()
}