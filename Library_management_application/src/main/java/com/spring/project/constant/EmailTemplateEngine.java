package com.spring.project.constant;

public enum EmailTemplateEngine {
    ACCOUNT_ACTIVATION("account_activation");

    private final String name;
    EmailTemplateEngine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
