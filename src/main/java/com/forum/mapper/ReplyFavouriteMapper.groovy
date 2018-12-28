package com.forum.mapper

import com.forum.model.entity.ReplyFavouriteEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface ReplyFavouriteMapper extends Mapper<ReplyFavouriteEntity> {
    @Select('select replyId,favourite from f_reply_favourite where replyId in (${replyId}) and sid = #{sid}')
    List<ReplyFavouriteEntity> SelectFavouriteByReplyIdGroupAndSid(
            @Param("replyId") String replyId, @Param("sid") String sid)

    @Select('select replyId,favourite,sid from f_reply_favourite where replyId = #{replyId} and sid = #{sid}')
    ReplyFavouriteEntity SelectFavouriteByReplyIdAndSid(
            @Param("replyId") String replyId, @Param("sid") String sid)


    @Select('select count(replyid) from f_reply_favourite where replyId = #{replyId} and sid = #{sid}')
    Integer SelectFavouriteCountByReplyIdAndSid(
            @Param("replyId") String replyId, @Param("sid") String sid)

    @Update('update f_reply_favourite set favourite = #{favourite} where replyId = #{replyid} and sid = #{sid}')
    Integer updateFavourite(ReplyFavouriteEntity replyFavouriteEntity)

    @Delete('delete from f_reply_favourite where replyId = #{replyid} and sid = #{sid}')
    Integer deleteFavourite(ReplyFavouriteEntity replyFavouriteEntity)

    @Insert('insert into f_reply_favourite (replyId,favourite,sid) values(#{replyid},#{favourite},#{sid})')
    Integer insertToTable(ReplyFavouriteEntity replyFavouriteEntity)
}