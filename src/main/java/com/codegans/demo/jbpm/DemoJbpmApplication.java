package com.codegans.demo.jbpm;

import org.drools.core.impl.EnvironmentFactory;
import org.drools.persistence.api.TransactionManager;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.factorybeans.RuntimeEnvironmentFactoryBean;
import org.kie.spring.persistence.KieSpringTransactionManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ImportResource(value = {
        "classpath:config/jpa-context.xml",
        "classpath:config/jbpm-context.xml",
        "classpath:config/security-context.xml"
})
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class DemoJbpmApplication {
    public static void main(String[] args) {
        System.setProperty("org.kie.txm.factory.class", KieSpringTransactionManagerFactory.class.getName());

        SpringApplication.run(DemoJbpmApplication.class, args);
    }

    @Bean
    public RuntimeEnvironment runtimeEnvironment(EntityManagerFactory entityManagerFactory, PlatformTransactionManager transactionManager) throws Exception {
        Map<Resource, ResourceType> assets = new HashMap<>();

        assets.put(ResourceFactory.newClassPathResource("bpmn/anomaly2case.bpmn2.xml"), ResourceType.BPMN2);

        RuntimeEnvironmentFactoryBean factoryBean = new RuntimeEnvironmentFactoryBean();

        factoryBean.setType(RuntimeEnvironmentFactoryBean.TYPE_DEFAULT);
        factoryBean.setEntityManagerFactory(entityManagerFactory);
        factoryBean.setTransactionManager(transactionManager);
        factoryBean.setAssets(assets);
        factoryBean.setEnvironmentEntries(Collections.singletonMap("IS_JTA_TRANSACTION", false));
        factoryBean.setPessimisticLocking(true);

        factoryBean.afterPropertiesSet();

        return (RuntimeEnvironment) factoryBean.getObject();
    }

    @Bean
    public TransactionManager kieTransactionManager(PlatformTransactionManager transactionManager) {
        Environment environment = EnvironmentFactory.newEnvironment();

        environment.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);
        environment.set("IS_JTA_TRANSACTION", false);

        return KieSpringTransactionManagerFactory.get().newTransactionManager(environment);
    }
}
