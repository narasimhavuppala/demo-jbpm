package com.codegans.demo.jbpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@ImportResource(value = {
        "classpath:config/jee-tx-context.xml",
        "classpath:config/jpa-context.xml",
        "classpath:config/jbpm-context.xml",
        "classpath:config/security-context.xml"
})
@SpringBootApplication
public class DemoJbpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJbpmApplication.class, args);
    }
}
