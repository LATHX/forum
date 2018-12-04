package com.forum.service.exception

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource

@PropertySource('classpath:config/configuration.properties')
class RequestLimitException extends Exception{

    private static final long serialVersionUID = 1555967171104727461L;

    public RequestLimitException(){
        super('刷新次数过多');
    }

    public RequestLimitException(String message){
        super(message);
    }
}
