package com.codegans.demo.jbpm.util;

import org.kie.internal.identity.IdentityProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 19/10/2017 17:25
 */
public class SpringSecurityIdentityProvider implements IdentityProvider {

    public String getName() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return "system";
    }

    public List<String> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            List<String> roles = new ArrayList<String>();

            for (GrantedAuthority ga : auth.getAuthorities()) {
                roles.add(ga.getAuthority());
            }

            return roles;
        }

        return Collections.emptyList();
    }

    public boolean hasRole(String role) {
        return false;
    }

}