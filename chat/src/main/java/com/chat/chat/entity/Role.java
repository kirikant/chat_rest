package com.chat.chat.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Property.READ)),
    ADMIN(Set.of(Property.READ,Property.REFACTOR));

    private Set<Property> properties;

     Role(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<SimpleGrantedAuthority> getProperties() {
        return properties.stream()
                .map(x->new SimpleGrantedAuthority(x.getProperty()))
                .collect(Collectors.toSet());
    }
}
