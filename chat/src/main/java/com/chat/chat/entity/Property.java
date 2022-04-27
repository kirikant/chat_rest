package com.chat.chat.entity;

public enum Property {
    READ("property:read"),
    REFACTOR("property:refactor");

    private String property;

    Property(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
