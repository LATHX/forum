package com.forum;

import com.forum.model.dto.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class His {
    @Autowired
    private LoginInfo loginInfo;
    @GetMapping("r")
    public LoginInfo f(){
        loginInfo.setPassword("121311");
        return loginInfo;
    }
}
