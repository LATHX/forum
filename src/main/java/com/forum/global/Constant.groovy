package com.forum.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

class Constant {
    final static Integer UUID_REDIS_CACHE_SIZE = 4
    final static String UUID_REDIS_QUEUE_NAME = 'token'
    final static Integer UUID_REDIS_KEY_TIMEOUT = 5
    final static String LOGIN_CODE_FREQUENT_MSG = '验证过于频繁'
    final static String LOGIN_CODE_FAIL_MSG = '验证失败'
    final static String LOGIN_CODE_SUCCESS_MSG = '验证通过'
}
