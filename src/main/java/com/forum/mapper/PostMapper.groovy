package com.forum.mapper

import com.forum.model.entity.PostEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface PostMapper extends Mapper<PostEntity> {
    @Select('select * from f_post where postid = #{postid}')
    PostEntity selectByPostId(@Param('postid') String postid)

    @Select('select * from f_post where creator = #{sid} order by date desc')
    List<PostEntity> selectBySId(@Param('sid') String sid)

    @Select("select title,postid from f_post where text like #{content}")
    List<PostEntity> selectByLikeText(@Param('content') String content)

    @Delete('delete from f_post where fid = #{fid} and postid = #{postid}')
    Integer deletePost(@Param('fid') String fid, @Param('postid') String postid)

    @Delete('delete from f_post where creator = #{sid} and postid = #{postid}')
    Integer deletePostBySIdAndPostId(@Param('sid') String sid, @Param('postid') String postid)
}