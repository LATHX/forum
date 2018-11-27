package com.forum.mapper

import com.forum.model.entity.UserEntity
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository
import tk.mybatis.mapper.annotation.RegisterMapper
import tk.mybatis.mapper.common.Mapper
@org.apache.ibatis.annotations.Mapper
@RegisterMapper
interface Test extends Mapper<UserEntity> {
    @Select('select * from f_user')
    UserEntity findPsw()
}