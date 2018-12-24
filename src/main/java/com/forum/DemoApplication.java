package com.forum;

import com.forum.service.config.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAutoConfiguration
@SpringBootApplication
//exclude = {DataSourceAutoConfiguration.class}
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
