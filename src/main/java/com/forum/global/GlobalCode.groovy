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
class GlobalCode implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(this.getClass())
    @Autowired
    DictionaryMapper dictionaryMapper

    @Override
    void afterPropertiesSet() throws Exception {
        load()
    }
    static String REFERENCE_SUCCESS
    static String REFERENCE_FAIL
    static String REFERENCE_DATA_COMPLETE
    void load() {
        logger.info('Initializing GlobalCode')
        List<DictionaryEntity> list = dictionaryMapper.selectAllInTableWithParamType('参数代码')
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
