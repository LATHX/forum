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
    final static Integer UUID_REDIS_CACHE_SIZE = 4
    final static String UUID_REDIS_QUEUE_NAME = 'token'
    final static Integer UUID_REDIS_KEY_TIMEOUT = 5
    String LOGIN_CODE_FREQUENT_MSG
    final static String LOGIN_CODE_FAIL_MSG = '验证失败'
    final static String LOGIN_CODE_SUCCESS_MSG = '验证通过'
    final static String LOGIN_CODE_TIMEOUT_MSG = '验证超时，请重新认证'
    final static String LOGIN_VERIFY_FAIL_MSG = '用户名或密码不正确'

    void setLOGIN_CODE_FREQUENT_MSG(String LOGIN_CODE_FREQUENT_MSG) {
        this.LOGIN_CODE_FREQUENT_MSG = LOGIN_CODE_FREQUENT_MSG
    }
}
