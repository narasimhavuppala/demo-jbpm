package com.codegans.demo.jbpm;

import org.kie.api.KieBase;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.utils.KieHelper;
import org.kie.spring.factorybeans.RuntimeEnvironmentFactoryBean;
import org.kie.spring.factorybeans.RuntimeManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 16.10.2017 23:06
 */
@EnableTransactionManagement
@Configuration
public class JbpmConfiguration {
    @Bean
    public KieBase createKnowledgeBase() {
        System.out.println("Creating KB");

        return new KieHelper()
                .addResource(ResourceFactory.newClassPathResource("bpmn/place-order.bpmn2.xml"), ResourceType.BPMN2)
                .addResource(ResourceFactory.newClassPathResource("bpmn/hardware-order.bpmn2.xml"), ResourceType.BPMN2)
                .build();
    }

    @Bean
    public RuntimeEnvironmentFactoryBean createRuntimeEnvironment(EntityManagerFactory entityManagerFactory, PlatformTransactionManager transactionManager) {
        RuntimeEnvironmentFactoryBean runtimeEnvironmentFactoryBean = new RuntimeEnvironmentFactoryBean();

        Map<Resource, ResourceType> assets = runtimeEnvironmentFactoryBean.getAssets();
        
        assets.put(ResourceFactory.newClassPathResource("bpmn/place-order.bpmn2.xml"), ResourceType.BPMN2);
        assets.put(ResourceFactory.newClassPathResource("bpmn/hardware-order.bpmn2.xml"), ResourceType.BPMN2);

        runtimeEnvironmentFactoryBean.setEntityManagerFactory(entityManagerFactory);
        runtimeEnvironmentFactoryBean.setTransactionManager(transactionManager);

        return runtimeEnvironmentFactoryBean;
    }

    @Bean
    public RuntimeManagerFactoryBean createRuntimeManager(RuntimeEnvironment runtimeEnvironment) {
        RuntimeManagerFactoryBean runtimeManagerFactoryBean = new RuntimeManagerFactoryBean();

        runtimeManagerFactoryBean.setRuntimeEnvironment(runtimeEnvironment);
        runtimeManagerFactoryBean.setIdentifier("com.codegans.demo.jbpm");

        return runtimeManagerFactoryBean;
    }

    @Bean
    public RuntimeEngine createRuntimeEngine(RuntimeManager runtimeManager) {
        return runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
    }
}
