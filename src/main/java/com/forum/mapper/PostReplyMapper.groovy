package com.forum.mapper

import com.forum.model.entity.PostReplyEntity
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface PostReplyMapper extends Mapper<PostReplyEntity> {
    @Select('select * from f_post_reply where replyId = #{replyId}')
    PostReplyEntity SelectByReplyId(@Param('replyId') String replyId)
}