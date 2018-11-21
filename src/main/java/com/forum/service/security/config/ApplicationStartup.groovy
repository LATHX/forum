package com.forum.service.security.config

import com.forum.service.security.encrypt.RSACryptoServiceProvider
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent

class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new RSACryptoServiceProvider().getInstance().generateKeyPair()
    }
}
