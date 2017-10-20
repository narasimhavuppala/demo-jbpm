package com.codegans.demo.jbpm.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 15:53
 */
public class ImprovedSpringPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {
    private static final String TABLE_PERFIX = "jbpm_";
    private static final String LOCAL_PACKAGE = "com.codegans.demo.jbpm.model.";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier identifier = super.toPhysicalTableName(name, jdbcEnvironment);

        String text = identifier.getText();

        if (text.startsWith(TABLE_PERFIX)) {
            return identifier;
        }

        try {
            Class.forName(LOCAL_PACKAGE + name.getText());

            return identifier;
        } catch (ClassNotFoundException e) {
            return new Identifier(TABLE_PERFIX + text, identifier.isQuoted());
        }
    }
}
