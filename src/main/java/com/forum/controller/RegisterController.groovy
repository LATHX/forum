package com.forum.controller

import com.forum.model.dto.RegisterInfo
import com.forum.model.validationInterface.RegisterGroup
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RegisterController {
    @GetMapping('/register')
    register(@Validated(value = [RegisterGroup.class]) RegisterInfo registerInfo, BindingResult bindingResult) {
        println bindingResult.getErrorCount()
    }
}
