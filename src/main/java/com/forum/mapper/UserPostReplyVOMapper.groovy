package com.forum.mapper

import com.forum.model.entity.UserPostReplyVOEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface UserPostReplyVOMapper {
    @Select('select user_img, f_post_reply.* from (select * from f_post_reply as a where favourite=(select max(b.favourite) from f_post_reply as b where a.postId = b.postId) and a.postId = (#{postId})) as f_post_reply ,f_user where f_post_reply.creator = sid and f_post_reply.enable=true')
    UserPostReplyVOEntity SelectMaxFavouriteReplyByPostId(@Param("postId") Integer postId)

    @Select('select user_img, f_post_reply.* from (select * from f_post_reply as a where favourite=(select max(b.favourite) from f_post_reply as b where a.postId = b.postId) and a.postId in (${postId})) as f_post_reply, f_user where f_post_reply.creator = sid and f_post_reply.enable=true')
    List<UserPostReplyVOEntity> SelectMaxFavouriteReplyByPostIdGroup(@Param("postId") String postId)

    @Select('select nickname, f_user.user_img, f_post_reply.* from f_post_reply,f_user where creator = sid and postId = #{postId}')
    List<UserPostReplyVOEntity> SelectPostReplyByPostId(@Param("postId") String postId)
}