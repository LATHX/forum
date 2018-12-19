package com.forum.mapper

import com.forum.model.entity.ForumListEntity
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface ForumListMapper extends Mapper<ForumListEntity> {
    @Select('select * from f_forumList')
    List<ForumListEntity> selectAllFromTable()

    @Select("select * from f_forumList where enable = #{enable} and authority = #{authority}")
    List<ForumListEntity> selectAllFromTableByEnableAndAuthority(@Param("enable")boolean enable,@Param("authority") boolean authority)
}