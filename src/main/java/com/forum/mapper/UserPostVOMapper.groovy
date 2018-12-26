package com.forum.mapper

import com.forum.model.entity.UserPostVOEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface UserPostVOMapper {
    @Select('select nickname,user_img,f_post.* from f_post,f_user where creator=sid and postId = #{postId}')
    UserPostVOEntity SelectPostByPostId(@Param('postId') String postId)
}