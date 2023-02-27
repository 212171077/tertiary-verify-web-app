package com.pc.entities.enums;

public enum TastStatusEnum {

    open("Open"),
    underway("Underway"),
    deleted("Deleted"),
    closed("Closed"),
    completed("Completed");

    private String displayName;

    TastStatusEnum(String displayNameX) {
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
