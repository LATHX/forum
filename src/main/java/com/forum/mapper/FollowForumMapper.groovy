package com.forum.mapper

import com.forum.model.entity.FollowForumEntity
import com.forum.model.entity.ForumListEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface FollowForumMapper {
    @Insert('insert into f_follow_forum (sid,fid) values (#{sid},#{fid})')
    Integer insert(FollowForumEntity followForumEntity)

    @Select('select count(*) from f_follow_forum where sid = #{sid} and fid = #{fid}')
    int selectCountBySIdAndFId(FollowForumEntity followForumEntity)

    @Delete('delete from f_follow_forum where sid = #{sid} and fid = #{fid}')
    Integer deleteBySIdAndFId(FollowForumEntity followForumEntity)

    @Select('select fname,fid from f_forumlist where f_forumlist.fid in (select fid from f_follow_forum where sid = #{sid})')
    List<ForumListEntity> selectAllFollowedForumBySId(@Param('sid') String sid)
}