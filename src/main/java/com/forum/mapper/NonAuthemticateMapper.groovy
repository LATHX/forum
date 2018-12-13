package com.forum.mapper

import com.forum.model.entity.NonAuthemticateEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface NonAuthemticateMapper extends Mapper<NonAuthemticateEntity> {
    @Select('select * from f_non_authemticate')
    List<NonAuthemticateEntity> selectAllFromTable()
}