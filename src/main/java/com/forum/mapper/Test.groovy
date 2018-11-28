package com.forum.mapper

import com.forum.model.entity.User
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.annotation.RegisterMapper
import tk.mybatis.mapper.common.Mapper
@org.apache.ibatis.annotations.Mapper
@RegisterMapper
interface Test extends Mapper<User> {
    @Select('select * from f_user')
    User findPsw()
}