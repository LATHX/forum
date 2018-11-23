package com.forum;

import com.forum.model.dto.LoginInfo;
import com.forum.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class His {
    @Autowired
    private LoginInfo loginInfo;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("r")
    public String f(){
        redisUtil.set("k", "kkkk");
        loginInfo.setPassword("121311");
        return redisUtil.get("k").toString();
    }
}
