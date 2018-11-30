package com.forum.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource('classpath:config/configuration.properties')
@ConfigurationProperties(prefix = "msg")
class Constant {
    Integer UUID_REDIS_CACHE_SIZE
    Integer UUID_REDIS_KEY_TIMEOUT
    String UUID_REDIS_QUEUE_NAME
    String LOGIN_CODE_FREQUENT_MSG
    String LOGIN_CODE_FAIL_MSG
    String LOGIN_CODE_SUCCESS_MSG
    String LOGIN_CODE_TIMEOUT_MSG
    String LOGIN_VERIFY_FAIL_MSG
    String LOGIN_PERMISSION_MSG
    String LOGIN_OUT_MSG
    String LOGIN_PAGE

    String getLOGIN_PAGE() {
        return LOGIN_PAGE
    }

    void setLOGIN_PAGE(String LOGIN_PAGE) {
        this.LOGIN_PAGE = LOGIN_PAGE
    }

    Integer getUUID_REDIS_CACHE_SIZE() {
        return UUID_REDIS_CACHE_SIZE
    }

    Integer getUUID_REDIS_KEY_TIMEOUT() {
        return UUID_REDIS_KEY_TIMEOUT
    }

    String getUUID_REDIS_QUEUE_NAME() {
        return UUID_REDIS_QUEUE_NAME
    }

    String getLOGIN_CODE_FREQUENT_MSG() {
        return LOGIN_CODE_FREQUENT_MSG
    }

    String getLOGIN_CODE_FAIL_MSG() {
        return LOGIN_CODE_FAIL_MSG
    }

    String getLOGIN_CODE_SUCCESS_MSG() {
        return LOGIN_CODE_SUCCESS_MSG
    }

    String getLOGIN_CODE_TIMEOUT_MSG() {
        return LOGIN_CODE_TIMEOUT_MSG
    }

    String getLOGIN_VERIFY_FAIL_MSG() {
        return LOGIN_VERIFY_FAIL_MSG
    }

    String getLOGIN_PERMISSION_MSG() {
        return LOGIN_PERMISSION_MSG
    }

    void setLOGIN_PERMISSION_MSG(String LOGIN_PERMISSION_MSG) {
        this.LOGIN_PERMISSION_MSG = LOGIN_PERMISSION_MSG
    }

    String getLOGIN_OUT_MSG() {
        return LOGIN_OUT_MSG
    }

    void setLOGIN_OUT_MSG(String LOGIN_OUT_MSG) {
        this.LOGIN_OUT_MSG = LOGIN_OUT_MSG
    }

    void setUUID_REDIS_CACHE_SIZE(Integer UUID_REDIS_CACHE_SIZE) {
        this.UUID_REDIS_CACHE_SIZE = UUID_REDIS_CACHE_SIZE
    }

    void setUUID_REDIS_KEY_TIMEOUT(Integer UUID_REDIS_KEY_TIMEOUT) {
        this.UUID_REDIS_KEY_TIMEOUT = UUID_REDIS_KEY_TIMEOUT
    }

    void setUUID_REDIS_QUEUE_NAME(String UUID_REDIS_QUEUE_NAME) {
        this.UUID_REDIS_QUEUE_NAME = UUID_REDIS_QUEUE_NAME
    }

    void setLOGIN_CODE_FREQUENT_MSG(String LOGIN_CODE_FREQUENT_MSG) {
        this.LOGIN_CODE_FREQUENT_MSG = LOGIN_CODE_FREQUENT_MSG
    }

    void setLOGIN_CODE_FAIL_MSG(String LOGIN_CODE_FAIL_MSG) {
        this.LOGIN_CODE_FAIL_MSG = LOGIN_CODE_FAIL_MSG
    }

    void setLOGIN_CODE_SUCCESS_MSG(String LOGIN_CODE_SUCCESS_MSG) {
        this.LOGIN_CODE_SUCCESS_MSG = LOGIN_CODE_SUCCESS_MSG
    }

    void setLOGIN_CODE_TIMEOUT_MSG(String LOGIN_CODE_TIMEOUT_MSG) {
        this.LOGIN_CODE_TIMEOUT_MSG = LOGIN_CODE_TIMEOUT_MSG
    }

    void setLOGIN_VERIFY_FAIL_MSG(String LOGIN_VERIFY_FAIL_MSG) {
        this.LOGIN_VERIFY_FAIL_MSG = LOGIN_VERIFY_FAIL_MSG
    }
}
