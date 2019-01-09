package com.forum.mapper

import com.forum.model.entity.FollowFriendEntity
import com.forum.model.entity.UserEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface FollowFriendMapper {
    @Insert('insert into f_follow_friends (sid,friend_sid) values (#{sid},#{friendSid})')
    Integer insert(FollowFriendEntity followFriendEntity)

    @Select('select count(sid) from f_follow_friends where sid = #{sid} and friend_sid = #{friendSid}')
    Integer selectCountBySIdAndFriendId(FollowFriendEntity followFriendEntity)

    @Delete('delete from f_follow_friends where sid = #{sid} and friend_sid = #{friendSid}')
    Integer deleteBySIdAndFriendId(FollowFriendEntity followFriendEntity)

    @Select('select f_user.sid,username from f_user where f_user.sid in (select friend_sid from f_follow_friends where f_follow_friends.sid = #{sid})')
    List<UserEntity> selectFriendListBySId(@Param('sid') String sid)
}