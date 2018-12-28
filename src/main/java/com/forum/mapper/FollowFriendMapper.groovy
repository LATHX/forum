package com.forum.mapper

import com.forum.model.entity.FollowFriendEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface FollowFriendMapper {
    @Insert('insert into f_follow_friends (sid,friend_sid) values (#{sid},#{friendSid})')
    Integer insert(FollowFriendEntity followFriendEntity)

    @Select('select count(sid) from f_follow_friends where sid = #{sid} and friend_sid = #{friendSid}')
    Integer selectCountBySIdAndFriendId(FollowFriendEntity followFriendEntity)

    @Delete('delete from f_follow_friends where sid = #{sid} and friend_sid = #{friendSid}')
    Integer deleteBySIdAndFriendId(FollowFriendEntity followFriendEntity)
}