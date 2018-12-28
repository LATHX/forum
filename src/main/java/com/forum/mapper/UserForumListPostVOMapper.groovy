package com.forum.mapper

import com.forum.model.entity.UserForumListPostVOEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface UserForumListPostVOMapper {
    @Select('select user_img,nickname,sid,f_post.postid,f_post.fid,f_post.type,f_post.title,f_post.text,f_post.lastupdatetime,f_post.video,f_post.img0,f_post.img1,f_post.img2,f_post.img3,f_post.img4,f_post.img5,f_post.img6,f_post.img7,f_post.img8,f_forumlist.fname,f_forumlist.img,f_forumlist.type as forum_type FROM f_user,f_forumlist,f_post where f_post.fid=#{fid} and f_post.fid=f_forumlist.fid and f_post.creator=sid and f_post.enable=true and f_forumlist.enable=true and f_forumlist.authority=true order by lastupdatetime desc')
    List<UserForumListPostVOEntity> userSingleForumPostList(@Param("fid") String fid)

    @Select('select user_img,nickname,sid,f_post.postid,f_post.fid,f_post.type,f_post.title,f_post.text,f_post.lastupdatetime,f_post.video,f_post.img0,f_post.img1,f_post.img2,f_post.img3,f_post.img4,f_post.img5,f_post.img6,f_post.img7,f_post.img8,f_forumlist.fname,f_forumlist.img,f_forumlist.type as forum_type FROM f_user,f_forumlist,f_post where f_post.fid=f_forumlist.fid and f_post.creator=sid and f_post.enable=true and f_forumlist.enable=true and f_forumlist.authority=true order by lastupdatetime desc')
    List<UserForumListPostVOEntity> userRecommendPostList()
}