package com.forum.mapper

import com.forum.model.entity.UserEntity
import com.forum.model.entity.UserFollowCountVOEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface UserMapper extends Mapper<UserEntity> {
    @Select('select nickname,sex,province,city,dist,user_img,user_background_img,userinfo_follow_count.* from f_user,userinfo_follow_count where f_user.sid=#{sid} and f_user.sid = userinfo_follow_count.sid')
    UserFollowCountVOEntity findUserBySid(String sid)

    @Select('select * from f_user where username=#{username}')
    UserEntity findUserByUserName(String username)

    @Insert('insert into f_user(username,password,nickname,sex,province,city,dist) values (#{username},#{password},#{nickname},#{sex},#{province},#{city},#{dist}) ')
    int insertIntoTable(UserEntity userEntity)

    @Select('select count(username) from f_user where username = #{username}')
    int countNumberByUsername(String username)

    @Select('select count(username) from f_user where sid = #{sid}')
    int selectCountBySId(@Param('sid') String sid)

    @Select('select username from f_user where sid = #{sid}')
    UserEntity selectUsernameBySId(@Param('sid') String sid)

    @Select('select enable from f_user where username = #{username}')
    boolean isAccountBlock(String username)

}