package com.mosque.masjedi.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(@NotNull Jwt jwt) {
        // Extract roles from the JWT token (Keycloak format)
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        // Safely extract roles with proper type checking
        List<String> roles = extractRoles(realmAccess);

        // Convert roles to Spring Security authorities
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    private List<String> extractRoles(Map<String, Object> realmAccess) {
        if (realmAccess == null || !realmAccess.containsKey("roles")) {
            return List.of(); // Return empty list if no roles found
        }

        Object rolesObject = realmAccess.get("roles");
        if (!(rolesObject instanceof List)) {
            throw new IllegalArgumentException("Expected 'roles' claim to contain a List");
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) rolesObject;

        // Validate all elements are strings
        if (!roles.stream().allMatch(item -> item instanceof String)) {
            throw new IllegalArgumentException("All roles must be Strings");
        }

        return roles;
    }
}