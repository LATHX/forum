package com.forum.mapper

import com.forum.model.entity.UserEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface UserMapper extends Mapper<UserEntity> {
    @Select('select * from f_user where username=#{username}')
    UserEntity findUserByUserName(String username)

    @Insert('INSERT INTO f_user(username,password,nickname,sex,province,city,dist) VALUES (#{username},#{password},#{nickname},#{sex},#{province},#{city},#{dist}) ')
    int insertIntoTable(UserEntity userEntity)

    @Select('select count(username) from f_user where username = #{username}')
    int countNumberByUsername(String username)

    @Select('select enable from f_user where username = #{username}')
    boolean isAccountBlock(String username)

}