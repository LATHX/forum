package com.forum.mapper

import com.forum.model.entity.PostEntity
import tk.mybatis.mapper.common.Mapper

@org.apache.ibatis.annotations.Mapper
interface PostMapper extends Mapper<PostEntity> {

}