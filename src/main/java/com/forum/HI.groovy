package com.forum

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HI {


    @RequestMapping("/hi")
    String hi(){
        return 'Hello World'
    }


}
