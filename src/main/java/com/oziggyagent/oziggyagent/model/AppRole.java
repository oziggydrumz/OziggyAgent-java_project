package com.oziggyagent.oziggyagent.model;

import lombok.Getter;

@Getter
public enum AppRole {
    USER ("user"),
    ADMIN("administrator"),
    SUPER_ADMIN("superAdministrator"),
    AGENT("agent");
    private final String value;


    AppRole(String value) {
        this.value = value;
    }
}
