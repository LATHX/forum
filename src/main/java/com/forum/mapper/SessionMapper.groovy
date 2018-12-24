package com.forum.mapper

import com.forum.model.entity.SessionEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface SessionMapper extends Mapper<SessionEntity> {
    @Select('select count(username) from f_session where username = #{username}')
    Integer SelectCountByUsername(@Param(value = "username") String username)

    @Select('select * from f_session where username = #{username}')
    List<SessionEntity> SelectAllByUsername(@Param(value = "username") String username)

    @Select('select cookie from f_session where username = #{username}')
    List<SessionEntity> SelectAllCookieByUsername(@Param(value = "username") String username)

    @Select('select * from f_session where username = #{username} and cookie = #{cookie}')
    SessionEntity SelectByUsernameAndCookie(
            @Param(value = "username") String username, @Param(value = "cookie") String cookie)

    @Select('select count(username) from f_session where username = #{username} and cookie = #{cookie}')
    Integer SelectCountByUsernameAndCookie(
            @Param(value = "username") String username, @Param(value = "cookie") String cookie)

    @Insert('insert into f_session (username, cookie, device, updatetime) values (#{username}, #{cookie}, #{device}, now())')
    Integer Insert(SessionEntity sessionEntity)

    @Delete('delete from f_session where username = #{username}')
    Integer DeleteAllByUsername(@Param(value = "username") String username)

    @Delete('delete from f_session where username= #{username} and cookie != #{cookie}')
    Integer DeleteOtherByUsername(SessionEntity sessionEntity)

    @Update('')
    Integer updateByPrimaryKeys()
}