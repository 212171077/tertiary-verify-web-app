package com.pc.entities.enums;

public enum UserRolesEnum {

    user("ROLE_USER"),
    admin("ROLE_ADMIN"),
    emp("ROLE_EMP");

    private String displayName;

    UserRolesEnum(String displayNameX) {
        displayName = displayNameX;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getFriendlyName() {
        return toString();
    }
}
