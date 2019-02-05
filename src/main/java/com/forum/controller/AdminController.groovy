package com.forum.controller

import com.forum.service.AdminService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "管理员", tags = ['管理员相关操作'])
@RestController
@RequestMapping('/admin')
class AdminController {
    @Autowired
    AdminService adminService

    @ApiOperation('获取所有论坛信息')
    @PostMapping('/getForumList')
    getForumList() {
        return adminService.getForumList()
    }

    @ApiOperation('论坛状态修改')
    @PostMapping('/editForum')
    editForum(String fid) {
        return adminService.editForum(fid)
    }

    @ApiOperation('获取论坛类型')
    @PostMapping('/getForumType')
    getForumType() {
        return adminService.getForumType()
    }

    @ApiOperation('修改论坛类型')
    @PostMapping('/editForumType')
    editForumType(String fid, String type) {
        return adminService.editForumType(fid, type)
    }

    @ApiOperation('获取所有用户信息')
    @PostMapping('/getUserList')
    editUserList() {
        return adminService.getUserList()
    }

    @ApiOperation('获取所有权限信息')
    @PostMapping('/getRole')
    getRole() {
        return adminService.getRole()
    }

    @ApiOperation('更改用户权限')
    @PostMapping('/editUserRole')
    editUserRole(String sid, String roleId) {
        return adminService.editUserRole(sid, roleId)
    }
}
