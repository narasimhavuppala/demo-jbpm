package com.codegans.demo.jbpm.util;

import org.hibernate.boot.model.naming.EntityNaming;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 15:53
 */
public class ImprovedSpringImplicitNamingStrategy extends SpringImplicitNamingStrategy {
    private static final String ENTITY_PREFIX = "Jbpm";
    private static final String[] JBPM_PACKAGES = {"org.kie.", "org.jbpm.", "org.drools."};

    @Override
    protected String transformEntityName(EntityNaming entityNaming) {
        String entityName = super.transformEntityName(entityNaming);

        for (String prefix : JBPM_PACKAGES) {
            if (entityNaming.getClassName().startsWith(prefix)) {
                return ENTITY_PREFIX + entityName;
            }
        }

        return entityName;
    }
}
