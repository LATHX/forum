package com.forum.mapper

import com.forum.model.entity.ForumListEntity
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface ForumListMapper extends Mapper<ForumListEntity> {
    @Select('select count(fid) from f_forumList where fid = #{fid}')
    Integer selectCountByFId(@Param('fid') String fid)

    @Select('select count(fid) from f_forumList where creator = #{sid}')
    Integer selectCountBySId(@Param('sid') String sid)

    @Select('select count(fid) from f_forumList where creator = #{sid} and fid = #{fid}')
    Integer selectCountBySIdAndFId(@Param('sid') String sid, @Param('fid') String fid)

    @Select('select fid from f_forumList where creator = #{sid}')
    String selectFIdBySId(@Param('sid') String sid)

    @Select('select * from f_forumList order by date desc')
    List<ForumListEntity> selectAllFromTable()

    @Select('select * from f_forumList where fid = #{fid}')
    ForumListEntity selectAllByFId(@Param('fid') String fid)

    //@Select("select * from f_forumlist where fid >= (select floor( max(fid) * rand()) from f_forumlist ) and enable = #{enable} and authority = #{authority} order by fid")
    @Select("select * from f_forumlist where enable = #{enable} and authority = #{authority} order by fid")
    List<ForumListEntity> selectAllFromTableByEnableAndAuthorityWithoutType(
            @Param("enable") boolean enable, @Param("authority") boolean authority)


    @Select("select * from f_forumlist where fid >= (select floor( max(fid) * rand()) from f_forumlist ) and enable = #{enable} and authority = #{authority} and type = #{type} order by fid")
    List<ForumListEntity> selectAllFromTableByEnableAndAuthorityWithType(
            @Param("enable") boolean enable, @Param("authority") boolean authority, @Param("type") boolean type)


    @Select('select fname from f_forumlist where fid = #{fid}')
    ForumListEntity selectNameByFid(@Param('fid') String fid)

    @Update('UPDATE f_forumList SET Enable = #{enable},Authority = #{authority} WHERE fid = #{fid}')
    Integer updateEnableByFid(
            @Param('enable') boolean enable, @Param('authority') boolean authority, @Param('fid') String fid)
}