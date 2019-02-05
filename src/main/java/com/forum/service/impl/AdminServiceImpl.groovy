package com.forum.service.impl

import com.forum.mapper.ForumListMapper
import com.forum.mapper.ForumTypeMapper
import com.forum.mapper.RoleMapper
import com.forum.mapper.UserMapper
import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.ForumTypeEntity
import com.forum.model.entity.RoleEntity
import com.forum.model.entity.UserEntity
import com.forum.service.AdminService
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl implements AdminService{
    @Autowired
    ForumListMapper forumListMapper
    @Autowired
    ForumTypeMapper forumTypeMapper
    @Autowired
    UserMapper userMapper
    @Autowired
    RoleMapper roleMapper

    @Override
    List<ForumListEntity> getForumList() {
        List<ForumListEntity> list = forumListMapper.selectAllFromTable()
        return list
    }

    @Override
    void editForum(String fid) {
        if (CommonUtil.isNotEmpty(fid)) {
            ForumListEntity forumListEntity = forumListMapper.selectAllByFId(fid)
            if (forumListEntity.getEnable() == false) {
                forumListEntity.setEnable(true)
                forumListEntity.setAuthority(true)
            } else if (forumListEntity.getEnable() == true) {
                forumListEntity.setEnable(false)
                forumListEntity.setAuthority(false)
            }
            forumListMapper.updateEnableByFid(forumListEntity.getEnable(), forumListEntity.getAuthority(), fid)
        }
    }

    @Override
    List<ForumTypeEntity> getForumType() {
        return forumTypeMapper.selectAll()
    }

    @Override
    void editForumType(String fid, String type) {
        ForumTypeEntity forumTypeEntity = forumTypeMapper.selectByPrimaryKey(type)
        if (CommonUtil.isNotEmpty(forumTypeEntity)) {
            ForumListEntity forumListEntity = new ForumListEntity()
            forumListEntity.setType(type)
            forumListEntity.setFid(fid)
            forumListMapper.updateByPrimaryKeySelective(forumListEntity)
        }
    }

    @Override
    List<UserEntity> getUserList() {
        return userMapper.selectAllFromTable()
    }

    @Override
    List<RoleEntity> getRole() {
        return roleMapper.selectAll()
    }

    @Override
    void editUserRole(String sid, String roleId) {
        UserEntity userEntity = new UserEntity()
        userEntity.setSid(sid)
        userEntity.setRoleId(roleId?.toInteger())
        userMapper.updateByPrimaryKeySelective(userEntity)
    }
}
