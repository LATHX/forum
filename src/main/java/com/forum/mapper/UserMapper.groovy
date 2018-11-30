package com.forum.mapper

import com.forum.model.entity.UserEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper
@org.apache.ibatis.annotations.Mapper
interface UserMapper extends Mapper<UserEntity> {
    @Select('select * from f_user where username=#{username}')
    UserEntity findUserByUserName(String username)


}