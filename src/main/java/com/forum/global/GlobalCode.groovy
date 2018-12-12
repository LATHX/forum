package com.forum.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource('classpath:config/configuration.properties')
@ConfigurationProperties(prefix = "code")
class GlobalCode {
    static String LOGIN_VERIFY_OK
    static String LOGIN_CODE_OK
    static String LOGIN_VERIFY_FAIL
    static String LOGIN_CODE_FAIL
    static String LOGIN_CODE_FREQUENT
    static String LOGIN_PERMISSION
    static String REGISTER_MAIL_FAIL
    static String REGISTER_MAIL_OK
    static String ACCOUNT_BLOCK
    @Value('${code.ACCOUNT_BLOCK}')
    void setACCOUNT_BLOCK(String ACCOUNT_BLOCK) {
        this.ACCOUNT_BLOCK = ACCOUNT_BLOCK
    }

    @Value('${code.REGISTER_MAIL_OK}')
    void setREGISTER_MAIL_OK(String REGISTER_MAIL_OK) {
        this.REGISTER_MAIL_OK = REGISTER_MAIL_OK
    }

    @Value('${code.REGISTER_MAIL_FAIL}')
    void setREGISTER_MAIL_FAIL(String REGISTER_MAIL_FAIL) {
        this.REGISTER_MAIL_FAIL = REGISTER_MAIL_FAIL
    }

    @Value('${code.LOGIN_VERIFY_OK}')
    void setLOGIN_VERIFY_OK(String LOGIN_VERIFY_OK) {
        this.LOGIN_VERIFY_OK = LOGIN_VERIFY_OK
    }
    @Value('${code.LOGIN_CODE_OK}')
    void setLOGIN_CODE_OK(String LOGIN_CODE_OK) {
        this.LOGIN_CODE_OK = LOGIN_CODE_OK
    }
    @Value('${code.LOGIN_VERIFY_FAIL}')
    void setLOGIN_VERIFY_FAIL(String LOGIN_VERIFY_FAIL) {
        this.LOGIN_VERIFY_FAIL = LOGIN_VERIFY_FAIL
    }
    @Value('${code.LOGIN_CODE_FAIL}')
    void setLOGIN_CODE_FAIL(String LOGIN_CODE_FAIL) {
        this.LOGIN_CODE_FAIL = LOGIN_CODE_FAIL
    }
    @Value('${code.LOGIN_CODE_FREQUENT}')
    void setLOGIN_CODE_FREQUENT(String LOGIN_CODE_FREQUENT) {
        this.LOGIN_CODE_FREQUENT = LOGIN_CODE_FREQUENT
    }
    @Value('${code.LOGIN_PERMISSION}')
    void setLOGIN_PERMISSION(String LOGIN_PERMISSION) {
        this.LOGIN_PERMISSION = LOGIN_PERMISSION
    }
}
