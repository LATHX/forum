package com.forum.mapper

import com.forum.model.entity.PostEntity
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface PostMapper extends Mapper<PostEntity> {
    @Select('select * from f_post where postid = #{postid}')
    PostEntity selectByPostId(@Param('postid')String postid)
}