package com.forum.mapper

import com.forum.model.entity.NotificationEntity
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface NotificationMapper extends Mapper<NotificationEntity> {
    @Select('select creator,creator_name,noun,updatetime,receiver from f_notification where receiver = #{receiver}')
    List<NotificationEntity> selectByReceiver(@Param('receiver') String receiver)

    @Select('select count(*) from f_notification where receiver = #{receiver}')
    Integer selectCountByReceiver(@Param('receiver') String receiver)

    @Delete('delete from f_notification where receiver = #{receiver}')
    Integer deleteByReceiver(@Param('receiver') String receiver)
}