package com.forum.controller

import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.RegisterGroup
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class RegisterController {
    @RequestMapping('/register')
    register(value = [RegisterGroup.class]RegisterInfo registerInfo, BindingResult bindingResult){
        println bindingResult.getErrorCount()
    }
}
