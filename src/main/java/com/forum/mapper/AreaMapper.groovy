package com.forum.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface AreaMapper {
    @Select('SELECT count(merger_name) FROM f_areas where merger_name=#{paramType}')
    int selectCountByMergeName(String paramType)
}