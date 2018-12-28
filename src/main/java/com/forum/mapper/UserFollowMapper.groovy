package com.forum.mapper

import com.forum.model.entity.UserInfoEntity
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface UserFollowMapper extends Mapper<UserInfoEntity> {
    @Select('select sid,friend_count,followed_count,forum_count from userinfo_follow_count where sid = #{sid}')
    UserInfoEntity selectBySid(@Param('sid') String sid)
}