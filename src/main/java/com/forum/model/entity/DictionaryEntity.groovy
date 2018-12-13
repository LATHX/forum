package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_dictionary')
class DictionaryEntity {
    Integer id
    String paramName
    String description
    String value
    String paramType

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getParamName() {
        return paramName
    }

    void setParamName(String paramName) {
        this.paramName = paramName
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getValue() {
        return value
    }

    void setValue(String value) {
        this.value = value
    }

    String getParamType() {
        return paramType
    }

    void setParamType(String paramType) {
        this.paramType = paramType
    }
}
