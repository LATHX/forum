package com.forum.global

import com.forum.mapper.DictionaryMapper
import com.forum.model.entity.DictionaryEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.lang.reflect.Field

@Component
class Constant implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(this.getClass())
    @Autowired
    DictionaryMapper dictionaryMapper

    @Override
    void afterPropertiesSet() throws Exception {
        load()
    }

    static String UUID_REDIS_CACHE_SIZE
    static String UUID_REDIS_KEY_TIMEOUT
    static String REGISTER_REDIS_TIMEOUT
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
    static String MQ_SEND_MAIL
    static String REGISTER_TITLE
    static String REGISTER_TEXT
    static String REGISTER_CODE
    static String REGISTER_PASSWORD
    static String REGISTER_SAME
    static String LOGIN_BLOCK_MSG
    static String LIMIT_COUNT
    static String LIMIT_TIMEOUT
    static String LIMIT_PATH
    static String ENABLE_SEND_MAIL
    static String REDIS_FORGOT_PASSWORD_NAME
    static String FORGOT_PASSWORD_TIMEOUT
    static String MQ_REDIS_DEL
    static String USERNAME_NOT_EXITS
    static String FORGOT_MAIL_SUBJECT
    static String FORGOT_MAIL_TEXT
    static String REST_PASSWORD_PAGE
    static String REST_PASSWORD_TIMEOUT_MSG
    static String REST_PASSWORD_FAIL_MSG
    static String REGISTER_VERIFY_AREA_FAIL
    void load() {
        logger.info('Initializing Constant')
        List<DictionaryEntity> list = dictionaryMapper.selectAllInTableWithoutParamType('参数代码')
        list.each { current_dictionary ->
            dynamicSet(current_dictionary.getParamName(), current_dictionary.getValue())
        }
    }

    private void dynamicSet(String propertyName, Object obj) {
        Field field = this.getClass().getDeclaredField(propertyName)
        field.setAccessible(true);
        field.set(this, obj);
    }


}
