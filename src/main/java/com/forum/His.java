package com.forum;

import com.forum.mapper.UserMapper;
import com.forum.model.dto.LoginInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forum.redis.util.RedisUtil;

@RestController
public class His {
    @Autowired
    private LoginInfo loginInfo;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper test;
    @GetMapping("r")
    public String f(){
        System.out.println( this.test.findUserByUserName("admin").getRoleId());
        redisUtil.set("k", "kkkk111");
        loginInfo.setPassword("121311");
        return redisUtil.get("k").toString();
    }
}
