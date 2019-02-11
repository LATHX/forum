package com.forum.service.impl

import com.forum.mapper.*
import com.forum.model.entity.*
import com.forum.service.AdminService
import com.forum.utils.CommonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl implements AdminService {
    @Autowired
    ForumListMapper forumListMapper
    @Autowired
    ForumTypeMapper forumTypeMapper
    @Autowired
    UserMapper userMapper
    @Autowired
    RoleMapper roleMapper
    @Autowired
    DictionaryMapper dictionaryMapper

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

    @Override
    void editUserStatus(String sid) {
        UserEntity userEntity = userMapper.selectEnableBySid(sid)
        if (userEntity?.getEnable() == false) {
            userEntity.setEnable(true)
        } else if (userEntity?.getEnable() == true) {
            userEntity.setEnable(false)
        }
        userMapper.updateEnableBySid(userEntity.getEnable(), sid)
    }

    @Override
    List<DictionaryEntity> getDictionary() {
        return dictionaryMapper.selectAll()
    }

    @Override
    Integer editForumType(String type) {
        ForumTypeEntity forumTypeEntity = forumTypeMapper.selectByPrimaryKey(type)
        if (CommonUtil.isEmpty(forumTypeEntity)) {
            ForumTypeEntity ForumTypeEntity1 = new ForumTypeEntity()
            ForumTypeEntity1.setType(type)
            return forumTypeMapper.insert(ForumTypeEntity1)
        } else {
            return forumTypeMapper.deleteByPrimaryKey(type)
        }
    }
}
