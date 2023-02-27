package com.pc.entities.enums;

public enum DocConfigLevelEnum {

    UserLevel("User Level"),
    CompanyLevel("Company Level");

    private String displayName;

    DocConfigLevelEnum(String displayNameX) {
        displayName = displayNameX;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return displayName;
    }

    public String getFriendlyName() {
        return toString();
    }
}
