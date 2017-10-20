package com.codegans.demo.jbpm;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.drools.core.impl.EnvironmentFactory;
import org.drools.persistence.api.TransactionManager;
import org.jbpm.kie.services.impl.AbstractDeploymentService;
import org.jbpm.services.api.DeploymentEventListener;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.spring.persistence.KieSpringTransactionManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collection;

@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@ImportResource(value = {
        "classpath:config/jpa-context.xml",
        "classpath:config/jbpm-context.xml",
        "classpath:config/security-context.xml",
        "classpath:config/asset-context.xml"
})
@SpringBootApplication
public class DemoJbpmApplication {
    public static void main(String[] args) {
        System.setProperty("org.kie.txm.factory.class", KieSpringTransactionManagerFactory.class.getName());

        SpringApplication.run(DemoJbpmApplication.class, args);
    }

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.enableNaming();

                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();

                resource.setName("jdbc/jbpm");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.h2.Driver");
                resource.setProperty("url", "jdbc:h2:file:~/demo-jbpm");
                resource.setProperty("username", "sa");
                resource.setProperty("password", "");

                context.getNamingResources().addResource(resource);
            }
        };
    }

    @Bean
    public Environment kieEnvironment() {
        return EnvironmentFactory.newEnvironment();
    }

    @Bean
    public TransactionManager kieTransactionManager(Environment environment, PlatformTransactionManager transactionManager) {
        environment.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);

        return KieSpringTransactionManagerFactory.get().newTransactionManager(environment);
    }

    @Bean
    public String addListener(AbstractDeploymentService deploymentService, Collection<DeploymentEventListener> listeners) {
        for (DeploymentEventListener listener : listeners) {
            deploymentService.addListener(listener);
        }

        return null;
    }
}
