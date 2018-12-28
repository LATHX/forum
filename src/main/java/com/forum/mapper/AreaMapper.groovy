package com.forum.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface AreaMapper {
    @Select('select count(merger_name) from f_areas where merger_name=#{paramType}')
    int selectCountByMergeName(String paramType)
}