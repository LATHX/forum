package com.forum.service

import com.forum.model.entity.ForumListEntity
import com.forum.model.entity.ForumTypeEntity
import com.forum.model.entity.RoleEntity
import com.forum.model.entity.UserEntity

interface AdminService {
    List<ForumListEntity> getForumList()
    void editForum(String fid)
    List<ForumTypeEntity> getForumType()
    void editForumType(String fid, String type)
    List<UserEntity> getUserList()
    List<RoleEntity> getRole()
    void editUserRole(String sid, String roleId)
}