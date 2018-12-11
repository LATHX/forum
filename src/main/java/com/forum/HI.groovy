package com.forum

import com.forum.global.Constant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HI {

    @Autowired
    private JavaMailSender javaMailSender;
    @RequestMapping("/hi")
    String hi() {
        return 'Hello World'
    }

    @RequestMapping('send')
    send(){
        Constant.MAIL_ADDRESS
    }


}
