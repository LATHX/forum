package com.forum;

import com.forum.model.dto.LoginInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forum.redis.util.RedisUtil;
import com.forum.mapper.Test;

@RestController
public class His {
    @Autowired
    private LoginInfo loginInfo;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Test test;
    @GetMapping("r")
    public String f(){
        System.out.println( (this.test.selectAll().get(0).getUsername()));
        redisUtil.set("k", "kkkk111");
        loginInfo.setPassword("121311");
        return redisUtil.get("k").toString();
    }
}
