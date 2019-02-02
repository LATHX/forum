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
}
