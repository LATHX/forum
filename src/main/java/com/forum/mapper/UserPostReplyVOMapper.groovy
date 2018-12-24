package com.forum.mapper

import com.forum.model.entity.UserPostReplyVOEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface UserPostReplyVOMapper {
    @Select('select user_img,f_post_reply.*,max(favourite) from f_post_reply,f_user where postId = #{postId} and sid=creator group by postId')
    UserPostReplyVOEntity SelectMaxFavouriteReplyByPostId(@Param("postId") Integer postId)

    @Select('select user_img,f_post_reply.*,max(favourite) from f_post_reply,f_user where postId in (${postId}) and sid=creator group by postId')
    List<UserPostReplyVOEntity> SelectMaxFavouriteReplyByPostIdGroup(@Param("postId") String postId)

}