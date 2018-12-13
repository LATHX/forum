package com.forum.mapper

import com.forum.model.entity.DictionaryEntity
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface DictionaryMapper extends Mapper<DictionaryEntity>{
    @Select('select param_name,value from f_dictionary where param_type <> #{paramType}')
    List<DictionaryEntity> selectAllInTableWithoutParamType(String paramType)
    @Select('select param_name,value from f_dictionary where param_type = #{paramType}')
    List<DictionaryEntity> selectAllInTableWithParamType(String paramType)
}