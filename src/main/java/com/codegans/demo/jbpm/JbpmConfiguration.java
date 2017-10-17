package com.codegans.demo.jbpm;

import org.kie.api.KieBase;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 16.10.2017 23:06
 */
@Configuration
public class JbpmConfiguration {
    @Bean
    public KieBase createKnowledgeBase() {
        System.out.println("Createing KB");

        return new KieHelper()
                .addResource(ResourceFactory.newClassPathResource("bpmn/place-order.bpmn2.xml"))
                .addResource(ResourceFactory.newClassPathResource("bpmn/hardware-order.bpmn2.xml"))
                .build();
    }
}
