package com.forum.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource('classpath:config/configuration.properties')
@ConfigurationProperties(prefix = "msg")
class Constant {
    static Integer UUID_REDIS_CACHE_SIZE
    static Integer UUID_REDIS_KEY_TIMEOUT
    static Integer REGISTER_REDIS_TIMEOUT
    static String UUID_REDIS_QUEUE_NAME
    static String LOGIN_CODE_FREQUENT_MSG
    static String LOGIN_CODE_FAIL_MSG
    static String LOGIN_CODE_SUCCESS_MSG
    static String LOGIN_CODE_TIMEOUT_MSG
    static String LOGIN_VERIFY_FAIL_MSG
    static String LOGIN_PERMISSION_MSG
    static String LOGIN_OUT_MSG
    static String LOGIN_PAGE
    static String LIMIT_MSG
    static String MAIL_ADDRESS
    static String REGISTER_MAIL_FAIL
    static String MQ_TOKEN_GENERATE
    static String REGISTER_REDIS_MAIL_NAME
    static String MQ_REGISTER_MAIL
    static String REGISTER_TITLE
    static String REGISTER_TEXT
    static String REGISTER_CODE
    static String REGISTER_PASSWORD
    static String REGISTER_SAME
    static String LOGIN_BLOCK_MSG
    @Value('${msg.LOGIN_BLOCK_MSG}')
    void setLOGIN_BLOCK_MSG(String loginBlockMsg) {
        LOGIN_BLOCK_MSG = loginBlockMsg
    }

    @Value('${msg.REGISTER_SAME}')
    void setREGISTER_SAME(String registerSame) {
        REGISTER_SAME = registerSame
    }

    @Value('${msg.REGISTER_CODE}')
    void setREGISTER_CODE(String REGISTER_CODE) {
        this.REGISTER_CODE = REGISTER_CODE
    }
    @Value('${msg.REGISTER_PASSWORD}')
    void setREGISTER_PASSWORD(String REGISTER_PASSWORD) {
        this.REGISTER_PASSWORD = REGISTER_PASSWORD
    }

    @Value('${msg.REGISTER_TITLE}')
    void setREGISTER_TITLE(String REGISTER_TITLE) {
        this.REGISTER_TITLE = REGISTER_TITLE
    }
    @Value('${msg.REGISTER_TEXT}')
    void setREGISTER_TEXT(String REGISTER_TEXT) {
        this.REGISTER_TEXT = REGISTER_TEXT
    }

    @Value('${msg.MQ_REGISTER_MAIL}')
    void setMQ_REGISTER_MAIL(String mqRegisterMail) {
        MQ_REGISTER_MAIL = mqRegisterMail
    }
    @Value('${msg.REGISTER_REDIS_TIMEOUT}')
    void setREGISTER_REDIS_TIMEOUT(Integer registerRedisTimeout) {
        REGISTER_REDIS_TIMEOUT = registerRedisTimeout
    }

    @Value('${msg.REGISTER_REDIS_MAIL_NAME}')
    void setREGISTER_REDIS_MAIL_NAME(String registerRedisMailName) {
        REGISTER_REDIS_MAIL_NAME = registerRedisMailName
    }

    @Value('${msg.REGISTER_MAIL_FAIL}')
    void setREGISTER_MAIL_FAIL(String registerMailFail) {
        REGISTER_MAIL_FAIL = registerMailFail
    }

    @Value('${msg.MQ_TOKEN_GENERATE}')
    void setMQ_TOKEN_GENERATE(String mqTokenGenerate) {
        MQ_TOKEN_GENERATE = mqTokenGenerate
    }

    @Value('${msg.MAIL_ADDRESS}')
    void setMAIL_ADDRESS(String MAIL_ADDRESS) {
        this.MAIL_ADDRESS = MAIL_ADDRESS
    }
    @Value('${msg.UUID_REDIS_CACHE_SIZE}')
    void setUUID_REDIS_CACHE_SIZE(Integer UUID_REDIS_CACHE_SIZE) {
        this.UUID_REDIS_CACHE_SIZE = UUID_REDIS_CACHE_SIZE
    }
    @Value('${msg.UUID_REDIS_KEY_TIMEOUT}')
    void setUUID_REDIS_KEY_TIMEOUT(Integer UUID_REDIS_KEY_TIMEOUT) {
        this.UUID_REDIS_KEY_TIMEOUT = UUID_REDIS_KEY_TIMEOUT
    }
    @Value('${msg.UUID_REDIS_QUEUE_NAME}')
    void setUUID_REDIS_QUEUE_NAME(String UUID_REDIS_QUEUE_NAME) {
        this.UUID_REDIS_QUEUE_NAME = UUID_REDIS_QUEUE_NAME
    }
    @Value('${msg.LOGIN_CODE_FREQUENT_MSG}')
    void setLOGIN_CODE_FREQUENT_MSG(String LOGIN_CODE_FREQUENT_MSG) {
        this.LOGIN_CODE_FREQUENT_MSG = LOGIN_CODE_FREQUENT_MSG
    }
    @Value('${msg.LOGIN_CODE_FAIL_MSG}')
    void setLOGIN_CODE_FAIL_MSG(String LOGIN_CODE_FAIL_MSG) {
        this.LOGIN_CODE_FAIL_MSG = LOGIN_CODE_FAIL_MSG
    }
    @Value('${msg.LOGIN_CODE_SUCCESS_MSG}')
    void setLOGIN_CODE_SUCCESS_MSG(String LOGIN_CODE_SUCCESS_MSG) {
        this.LOGIN_CODE_SUCCESS_MSG = LOGIN_CODE_SUCCESS_MSG
    }
    @Value('${msg.LOGIN_CODE_TIMEOUT_MSG}')
    void setLOGIN_CODE_TIMEOUT_MSG(String LOGIN_CODE_TIMEOUT_MSG) {
        this.LOGIN_CODE_TIMEOUT_MSG = LOGIN_CODE_TIMEOUT_MSG
    }
    @Value('${msg.LOGIN_VERIFY_FAIL_MSG}')
    void setLOGIN_VERIFY_FAIL_MSG(String LOGIN_VERIFY_FAIL_MSG) {
        this.LOGIN_VERIFY_FAIL_MSG = LOGIN_VERIFY_FAIL_MSG
    }
    @Value('${msg.LOGIN_PERMISSION_MSG}')
    void setLOGIN_PERMISSION_MSG(String LOGIN_PERMISSION_MSG) {
        this.LOGIN_PERMISSION_MSG = LOGIN_PERMISSION_MSG
    }
    @Value('${msg.LOGIN_OUT_MSG}')
    void setLOGIN_OUT_MSG(String LOGIN_OUT_MSG) {
        this.LOGIN_OUT_MSG = LOGIN_OUT_MSG
    }
    @Value('${msg.LOGIN_PAGE}')
    void setLOGIN_PAGE(String LOGIN_PAGE) {
        this.LOGIN_PAGE = LOGIN_PAGE
    }
    @Value('${msg.LIMIT_MSG}')
    void setLIMIT_MSG(String LIMIT_MSG) {
        this.LIMIT_MSG = LIMIT_MSG
    }
}
