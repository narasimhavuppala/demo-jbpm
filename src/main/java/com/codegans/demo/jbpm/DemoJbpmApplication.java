package com.codegans.demo.jbpm;

import org.drools.core.impl.EnvironmentFactory;
import org.drools.persistence.api.TransactionManager;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.spring.persistence.KieSpringTransactionManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

@ImportResource(value = {
        "classpath:config/jpa-context.xml",
        "classpath:config/jbpm-context.xml",
        "classpath:config/security-context.xml"
})
@EnableJpaRepositories
@SpringBootApplication
public class DemoJbpmApplication {
    public static void main(String[] args) {
        System.setProperty("org.kie.txm.factory.class", KieSpringTransactionManagerFactory.class.getName());

        SpringApplication.run(DemoJbpmApplication.class, args);
    }

    @Bean
    public TransactionManager kieTransactionManager(PlatformTransactionManager transactionManager) {
        Environment environment = EnvironmentFactory.newEnvironment();

        environment.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);

        return KieSpringTransactionManagerFactory.get().newTransactionManager(environment);
    }
}
