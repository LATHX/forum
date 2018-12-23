package com.forum.mapper

import com.forum.model.entity.SessionEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

interface SessionMapper extends Mapper<SessionEntity> {
    @Select('select count(username) from f_session where username = #{username}')
    Integer SelectCountByUsername(@Param(value = "username") String username)

    @Insert('insert into f_session (username, cookie, updatetime) values (#{username}, #{cookie}, now())')
    Integer Insert(SessionEntity sessionEntity)

    @Delete('delete from f_session where username = #{username}')
    Integer DeleteAllByUsername(@Param(value = "username") String username)

    @Delete('delete from f_session where username= #{username} and cookie != #{cookie}')
    Integer DeleteOtherByUsername(SessionEntity sessionEntity)
}