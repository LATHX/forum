package com.forum.global

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource('classpath:config/configuration.properties')
@ConfigurationProperties(prefix = "code")
class GlobalCode {
    String LOGIN_VERIFY_OK
    String LOGIN_CODE_OK

    String LOGIN_VERIFY_FAIL
    String LOGIN_CODE_FAIL
    String LOGIN_CODE_FREQUENT

    void setLOGIN_VERIFY_OK(String LOGIN_VERIFY_OK) {
        this.LOGIN_VERIFY_OK = LOGIN_VERIFY_OK
    }

    void setLOGIN_CODE_OK(String LOGIN_CODE_OK) {
        this.LOGIN_CODE_OK = LOGIN_CODE_OK
    }

    void setLOGIN_VERIFY_FAIL(String LOGIN_VERIFY_FAIL) {
        this.LOGIN_VERIFY_FAIL = LOGIN_VERIFY_FAIL
    }

    void setLOGIN_CODE_FAIL(String LOGIN_CODE_FAIL) {
        this.LOGIN_CODE_FAIL = LOGIN_CODE_FAIL
    }

    void setLOGIN_CODE_FREQUENT(String LOGIN_CODE_FREQUENT) {
        this.LOGIN_CODE_FREQUENT = LOGIN_CODE_FREQUENT
    }
}
